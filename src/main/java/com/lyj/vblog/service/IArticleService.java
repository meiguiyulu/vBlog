package com.lyj.vblog.service;

import com.lyj.vblog.dos.Archives;
import com.lyj.vblog.params.ArticleParam;
import com.lyj.vblog.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyj.vblog.vo.ArticleVo;
import com.lyj.vblog.params.PageParams;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
public interface IArticleService extends IService<Article> {

    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */
    List<ArticleVo> listArticle(PageParams pageParams);

    /**
     * 最热文章
     * @param limit
     * @return
     */
    List<ArticleVo> findHotArticles(int limit);

    /**
     * 最新文章
     * @param limit
     * @return
     */
    List<ArticleVo> findNewArticles(int limit);


    /**
     * 首页 文章归档
     * @return
     */
    List<Archives> listArchives();

    /**
     * 文章详情
     * @param id
     * @return
     */
    ArticleVo findArticleById(Long id);

    /**
     * 发表文章
     * @param param
     * @return
     */
    ArticleVo publish(ArticleParam param);
}
