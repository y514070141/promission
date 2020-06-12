package com.itlike.service.impl;

import com.itlike.domain.Permission;
import com.itlike.mapper.PermissionMapper;
import com.itlike.service.permissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class permissionServiceImpl implements permissionService {
//    注入mapper
    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public List<Permission> getPermission() {
        return permissionMapper.selectAll();
    }
}
