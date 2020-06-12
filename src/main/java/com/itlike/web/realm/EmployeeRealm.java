package com.itlike.web.realm;

import com.itlike.domain.Employee;
import com.itlike.service.employeeService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRealm extends AuthorizingRealm {
    //注入service
    @Autowired
    private employeeService employeeService;
    //授权oriza
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //什么时候此方法会调用
        //1.方法访问路径对应的方法上面有注解--------  @RequiresPermissions("employee:index")
        //2.页面当中 有 授权标签----------配置文件扫描
        System.out.println("授权调用-------");
        //获取用户身份信息
        Employee employee=(Employee) principalCollection.getPrimaryPrincipal();
//        根据当前 身份查看角色 和权限
        List<String> roles=new ArrayList<>();
        List<String> permissions=new ArrayList<>();
        //判断用户是不是 管理员   管理员 有所有权限
        if(employee.getAdmin()){
            //拥有所有权限
            permissions.add("*:*");
        }else{
            //        查询角色
            roles=employeeService.getRolesRoleId(employee.getId());
            //System.out.println("roles="+roles);
//        查询权限
            permissions=employeeService.getPermissionById(employee.getId());
            //System.out.println("permissions="+permissions);
        }
        // 给授权信息
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(permissions);
        return info;
    }
    //认证entic
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户名
        String username=(String) token.getPrincipal();
        //查询数据库
        Employee employee =employeeService.getEmployeeByUserName(username);
        System.out.println(employee);
        if(employee==null){
            return null;
        }
        //盐
        ByteSource salt = ByteSource.Util.bytes(employee.getUsername());
//        salt,
        //参数   主体 凭据 盐 realm名字
        SimpleAuthenticationInfo info=
                new SimpleAuthenticationInfo(employee,
                        employee.getPassword(),
                        salt,
                        this.getName());
        return info;
    }
}
