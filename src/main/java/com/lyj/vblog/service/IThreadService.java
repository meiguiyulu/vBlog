package com.lyj.vblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyj.vblog.common.aop.LogAnnotation;
import com.lyj.vblog.mapper.ArticleMapper;
import com.lyj.vblog.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Component
public class IThreadService {

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 阅读量 + 1
     *
     * @param article
     */
    @Async("taskExecutor")
    public void updateReadView(Article article) {
        Integer viewCounts = article.getViewCounts();
        article.setViewCounts(viewCounts + 1);
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>()
                .eq("id", article.getId())
                .eq("view_counts", viewCounts); // 类似于乐观锁
        articleMapper.update(article, wrapper);
/*        try {
            //睡眠5秒 证明不会影响主线程的使用
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * 评论量 + 1
     *
     * @param articleId
     */
    @Async("taskExecutor")
    public void updateCommentView(Long articleId) {
        Article article = articleMapper.selectById(articleId);
        Integer commentCounts = article.getCommentCounts();
        article.setCommentCounts(commentCounts + 1);
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>()
                .eq("id", articleId)
                .eq("comment_counts", commentCounts);
        articleMapper.update(article, wrapper);
    }
}
