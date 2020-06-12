package com.itlike.service.impl;

import com.itlike.domain.Department;
import com.itlike.mapper.DepartmentMapper;
import com.itlike.service.departmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class departmentServiceImpl implements departmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Override
    public List<Department> getDepartment() {
        return departmentMapper.selectAll();
    }
}
