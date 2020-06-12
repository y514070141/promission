package com.itlike.mapper;

import com.itlike.domain.Employee;
import com.itlike.domain.QueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll(QueryVo queryVo);

    int updateByPrimaryKey(Employee record);

    void updateState(Long id);

    void insertEmpAndRole(@Param("id") Long id, @Param("rid") Long rid);

    List<Long> getRidByEid(Long id);

    void deleteEmpAndRoleRel(Long id);

    //根据
    Employee getEmployeeWithUsername(String username);

    List<String> getRolesRoleId(Long id);

    List<String> getPermissionById(Long id);
}