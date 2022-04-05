package com.lyj.vblogadmin.params;

import lombok.Data;

import java.util.List;

@Data
public class pageResult<T> {

    private List<T> list;
    private Long total;

}
