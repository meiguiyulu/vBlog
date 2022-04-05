package com.lyj.vblog.controller;


import com.lyj.vblog.common.Result;
import com.lyj.vblog.common.aop.Cache;
import com.lyj.vblog.common.aop.LogAnnotation;
import com.lyj.vblog.params.ArticleParam;
import com.lyj.vblog.service.IArticleService;
import com.lyj.vblog.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
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
     *
     * @param pageParams
     * @return
     */
    @PostMapping
    @LogAnnotation(module="文章", operator="获取文章列表") // 加上此注解 表示要对此接口记录日志
    public Result listArticle(@RequestBody PageParams pageParams) {
        return Result.success(articleService.listArticle(pageParams));
    }

    /**
     * 首页 最热文章
     *
     * @return
     */
    @PostMapping("/hot")
    @Cache(expire = 2 * 60 * 1000, name = "hot_articles")
    public Result hotArticles() {
        int limit = 5; // 取前5条
        return Result.success(articleService.findHotArticles(limit));
    }

    /**
     * 首页 最新文章
     *
     * @return
     */
    @PostMapping("/new")
    @Cache(expire = 2 * 60 * 1000, name = "new_articles")
    public Result newArticles() {
        int limit = 5; // 取前5条
        return Result.success(articleService.findNewArticles(limit));
    }

    /**
     * 首页 文章归档
     *
     * @return
     */
    @PostMapping("/listArchives")
    public Result listArchives() {
        return Result.success(articleService.listArchives());
    }

    /**
     * 文章详情
     *
     * @param id
     * @return
     */
    @PostMapping("/view/{id}")
    public Result findArticleById(@PathVariable("id") Long id) {
        return Result.success(articleService.findArticleById(id));
    }

    /**
     * 发布文章
     * @return param
     */
    @PostMapping("/publish")
    public Result publish(@RequestBody ArticleParam param){
        return Result.success(articleService.publish(param));
    }

}
