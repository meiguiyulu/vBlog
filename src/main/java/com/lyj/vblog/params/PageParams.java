package com.lyj.vblog.params;

import lombok.Data;

@Data
public class PageParams {
    private Integer currPage = 1;
    private Integer pageSize = 10;

    private Long categoryId;
    private Long tagId;

    private String year;
    private String month;

    public String getMonth() {
        if (month != null && month.length() == 1) {
            return "0" + month;
        }
        return month;
    }
}
