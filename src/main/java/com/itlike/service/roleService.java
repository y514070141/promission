package com.itlike.service;

import com.itlike.domain.Permission;
import com.itlike.domain.QueryVo;
import com.itlike.domain.Role;
import com.itlike.domain.pageListRes;

import java.util.List;

public interface roleService {
    public pageListRes getRoles(QueryVo vo);

    void insertRoleAndPromission(Role role);

    List<Permission> selectPermissionById(Long rid);

    void updatePermissionByRid(Role role);

    void deleteRole(Long rid);

    List<Role> selectAllRole();

}
