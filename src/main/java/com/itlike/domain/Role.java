package com.itlike.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Role {
    private Long rid;

    private String rnum;

    private String rname;

    //存放用户角色权限
    private List<Permission> permissions=new ArrayList<>();
}