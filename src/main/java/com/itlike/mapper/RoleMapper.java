package com.itlike.mapper;

import com.itlike.domain.Permission;
import com.itlike.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long rid);

    int insert(Role record);

    Role selectByPrimaryKey(Long rid);

    List<Role> selectAll();

    //修改角色
    int updateByPrimaryKey(Role role);

    void insertPermission(@Param("rid") Long rid, @Param("pid") Long pid);

    List<Permission> selectByRid(Long rid);

    void deleteRoleByRid(Long rid);
}