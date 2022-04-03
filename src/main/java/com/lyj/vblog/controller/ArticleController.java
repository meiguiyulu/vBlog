package com.lyj.vblog.controller;


import com.lyj.vblog.common.Result;
import com.lyj.vblog.service.IArticleService;
import com.lyj.vblog.vo.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    /**
     * 首页 文章列表
     * @param pageParams
     * @return
     */
    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams){
        return Result.success(articleService.listArticle(pageParams));
    }

}
