package com.itlike.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class pageListRes {
    private long total;
    private List<?> rows=new ArrayList<>();
}
