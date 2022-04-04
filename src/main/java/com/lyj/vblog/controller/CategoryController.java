package com.lyj.vblog.controller;


import com.lyj.vblog.common.Result;
import com.lyj.vblog.service.ICategoryService;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
@RestController
@RequestMapping("/categorys")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    /**
     * 所有类别
     *
     * @return
     */
    @GetMapping
    public Result categories() {
        return Result.success(categoryService.findAll());
    }

    /**
     * 文章分类的详情  其实这两个方法完全可以写成   @GetMapping({"", "/detail"})
     */
    @GetMapping("/detail")
    public Result detail() {
        return categories();
    }

}
