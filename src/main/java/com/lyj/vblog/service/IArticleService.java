package com.lyj.vblog.service;

import com.lyj.vblog.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyj.vblog.vo.ArticleVo;
import com.lyj.vblog.vo.PageParams;

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
}
