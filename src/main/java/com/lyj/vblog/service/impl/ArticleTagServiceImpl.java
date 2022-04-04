package com.lyj.vblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyj.vblog.pojo.ArticleTag;
import com.lyj.vblog.mapper.ArticleTagMapper;
import com.lyj.vblog.service.IArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements IArticleTagService {

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public List<ArticleTag> findByTagId(Long tagId) {
        QueryWrapper<ArticleTag> tagQueryWrapper = new QueryWrapper<ArticleTag>()
                .eq("tag_id",tagId);
        List<ArticleTag> articleTags = articleTagMapper.selectList(tagQueryWrapper);
        return articleTags;
    }
}
