package com.lyj.vblog.params;

import lombok.Data;

@Data
public class PageParams {
    private Integer currPage = 1;
    private Integer pageSize = 10;
}
