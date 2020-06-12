package com.itlike.service;

import com.itlike.domain.Employee;
import com.itlike.domain.QueryVo;
import com.itlike.domain.pageListRes;

import java.util.List;

public interface employeeService {
    //分页 需要两个参数
    public pageListRes getEmployee(QueryVo queryVo);

    //保存员工
    public void saveEmployee(Employee employee);

    public void updateEmployee(Employee employee);

    void updateState(Long id);

    List<Long> getRidByEid(Long id);
    //根据用户名 查询用户
    Employee getEmployeeByUserName(String username);

    //根据用户id查询角色编号名称
    List<String> getRolesRoleId(Long id);
    //根据用户id查询资源名称那个
    List<String> getPermissionById(Long id);
}
