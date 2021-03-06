package com.lyj.vblog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lyj.vblog.pojo.SysUser;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String title;

    private String summary;

    private int commentCounts;

    private int viewCounts;

    private int weight;
    /**
     * 创建时间
     */
    private Date createDate;

    private SysUser author;

    private ArticleBodyVo body;

    private List<TagVo> tags;

    private CategoryVo categoryVo;
}
