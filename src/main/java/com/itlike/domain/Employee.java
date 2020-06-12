package com.itlike.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Employee {
    private Long id;
    private String username;
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inputtime;

    private String tel;

    private String email;

    private Boolean state=true;

    private Boolean admin;

    private Department department;

    //存放角色权限
    private List<Role> roles=new ArrayList<>();
}