package com.itlike.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itlike.domain.Employee;
import com.itlike.domain.QueryVo;
import com.itlike.domain.Role;
import com.itlike.domain.pageListRes;
import com.itlike.mapper.EmployeeMapper;
import com.itlike.service.employeeService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class employeeServiceImpl implements employeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Override
    public pageListRes getEmployee(QueryVo queryVo) {
        //分页查询    1.先设置页数，条数格式   2.在查询
        Page<Object> page = PageHelper.startPage(queryVo.getPage(), queryVo.getRows());
        List<Employee> employeeList = employeeMapper.selectAll(queryVo);
        //封装成PageList
        pageListRes pageListRes=new pageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(employeeList);
        return pageListRes;
    }

    @Override
    public void saveEmployee(Employee employee) {
        //实现密码登录 保存
        //1.设置 存储加密方式  2.配置认证处理加密方式 3.配置凭证匹配器
        //密码加密   密码  加盐 2次散列
        Md5Hash md5Hash=new Md5Hash(employee.getPassword(),employee.getUsername(),2);
        employee.setPassword(md5Hash.toString());
        //1.保存员工
        employeeMapper.insert(employee);
        //2.保存员工角色
        for (Role role : employee.getRoles()) {
            employeeMapper.insertEmpAndRole(employee.getId(),role.getRid());
        }
    }

    //更新员工
    @Override
    public void updateEmployee(Employee employee) {
        //1.打破员工角色关系 2.修改员工 3.增加员工角色关系
        employeeMapper.deleteEmpAndRoleRel(employee.getId());
        employeeMapper.updateByPrimaryKey(employee);
        //员工id一个 角色id多个
        for (Role role : employee.getRoles()) {
            employeeMapper.insertEmpAndRole(employee.getId(),role.getRid());
        }
    }

    @Override
    public void updateState(Long id) {
        employeeMapper.updateState(id);
    }

    @Override
    public List<Long> getRidByEid(Long id) {
        return employeeMapper.getRidByEid(id);
    }

    //根据用户名 查询用户
    @Override
    public Employee getEmployeeByUserName(String username) {
        Employee employeeWithUsername = employeeMapper.getEmployeeWithUsername(username);
        return employeeWithUsername;
    }

    @Override
    public List<String> getRolesRoleId(Long id) {
       return employeeMapper.getRolesRoleId(id);
    }

    @Override
    public List<String> getPermissionById(Long id) {
        return employeeMapper.getPermissionById(id);
    }


}
