package com.itlike.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itlike.domain.Permission;
import com.itlike.domain.QueryVo;
import com.itlike.domain.Role;
import com.itlike.domain.pageListRes;
import com.itlike.mapper.RoleMapper;
import com.itlike.service.roleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class roleServiceImpl implements roleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public pageListRes getRoles(QueryVo vo) {
        Page<Object> page = PageHelper.startPage(vo.getPage(), vo.getRows());
        List<Role> roles = roleMapper.selectAll();
        //封装
        pageListRes pageListRes=new pageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(roles);
        return pageListRes;
    }

    @Override
    public void insertRoleAndPromission(Role role) {
        //1.保存 角色
        roleMapper.insert(role);
        //2.保存 角色关系
        for (Permission permission : role.getPermissions()) {
            roleMapper.insertPermission(role.getRid(),permission.getPid());
        }
    }

    @Override
    public List<Permission> selectPermissionById(Long rid) {
        List<Permission> permissions = roleMapper.selectByRid(rid);
        return permissions;
    }

    @Override
    public void updatePermissionByRid(Role role) {
        //1.打破关系
        int i = roleMapper.deleteByPrimaryKey(role.getRid());
        if(i>0){
            //2.修改角色
            int i1 = roleMapper.updateByPrimaryKey(role);
            if(i1>0){
                //System.out.println("权限permission"+role.getPermissions());
                //3.新增权限关系
                for (Permission permission : role.getPermissions()) {
                    roleMapper.insertPermission(role.getRid(),permission.getPid());
                }
            }
        }
    }

    @Override
    public void deleteRole(Long rid) {
        //1.删除角色权限 2.删除角色
        roleMapper.deleteByPrimaryKey(rid);
        roleMapper.deleteRoleByRid(rid);
    }

    @Override
    public List<Role> selectAllRole() {
        //角色 下拉框
        return roleMapper.selectAll();
    }
}
