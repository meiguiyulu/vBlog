package com.lyj.vblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyj.vblog.mapper.ArticleMapper;
import com.lyj.vblog.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class IThreadService {

    @Autowired
    private ArticleMapper articleMapper;

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
}
