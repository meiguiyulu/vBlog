package com.lyj.vblog.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentVo  {

    private Long id;

    private UserVo author;

    private String content;

    private List<CommentVo> children;

    private Date createDate;

    private Integer level;

    private UserVo toUser;
}