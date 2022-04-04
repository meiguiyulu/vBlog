package com.lyj.vblog.controller;


import com.lyj.vblog.common.Result;
import com.lyj.vblog.service.ICommentService;
import com.lyj.vblog.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    ICommentService commentService;

    @GetMapping("/article/{id}")
    public Result comments(@PathVariable("id") Long id) {
        List<CommentVo> list = commentService.commentsByArticleId(id);
        return Result.success(list);
    }

}
