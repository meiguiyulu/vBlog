package com.lyj.vblog.controller;


import com.lyj.vblog.common.Result;
import com.lyj.vblog.pojo.Tag;
import com.lyj.vblog.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private ITagService tagService;

    @GetMapping("hot")
    public Result hotTags(){
        /*前6个最热标签*/
        int limit = 6;
        List<Tag> tagList = tagService.findHotTags(limit);
        return Result.success(tagList);
    }

    /**
     * 所有标签
     * @return
     */
    @GetMapping
    public Result tag(){
        return Result.success(tagService.findAll());
    }

}
