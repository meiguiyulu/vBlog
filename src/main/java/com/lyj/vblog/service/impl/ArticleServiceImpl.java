package com.lyj.vblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyj.vblog.dos.Archives;
import com.lyj.vblog.mapper.ArticleBodyMapper;
import com.lyj.vblog.mapper.CategoryMapper;
import com.lyj.vblog.params.ArticleParam;
import com.lyj.vblog.params.PageParams;
import com.lyj.vblog.pojo.*;
import com.lyj.vblog.mapper.ArticleMapper;
import com.lyj.vblog.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyj.vblog.utils.ThreadLocalUtil;
import com.lyj.vblog.vo.ArticleBodyVo;
import com.lyj.vblog.vo.ArticleVo;
import com.lyj.vblog.vo.CategoryVo;
import com.lyj.vblog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ITagService tagService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private IThreadService threadService;

    @Autowired
    private IArticleTagService articleTagService;

    /**
     * 分页查询文章列表
     *
     * @param pageParams
     * @return
     */
    @Override
    public List<ArticleVo> listArticle(PageParams pageParams) {
        Page page = new Page(pageParams.getCurrPage(), pageParams.getPageSize());
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>()
                .orderByDesc("create_date", "weight");
        Page selectPage = articleMapper.selectPage(page, wrapper);
        List<Article> records = selectPage.getRecords();

        List<ArticleVo> articleVos = copyList(records, true, true);
        return articleVos;
    }

    @Override
    public List<ArticleVo> findHotArticles(int limit) {
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>()
                .orderByDesc("view_counts")
                .last("limit " + limit);
        List<Article> articles = articleMapper.selectList(wrapper);

        List<ArticleVo> articleVos = copyList(articles, false, false);
        return articleVos;
    }

    @Override
    public List<ArticleVo> findNewArticles(int limit) {
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>()
                .orderByDesc("create_date")
                .last("limit " + limit);
        List<Article> articles = articleMapper.selectList(wrapper);

        List<ArticleVo> articleVos = copyList(articles, false, false);
        return articleVos;
    }

    /**
     * 文章归档
     *
     * @return
     */
    @Override
    public List<Archives> listArchives() {
        return articleMapper.listArchives();
    }

    /**
     * 文章详情
     *
     * @param id
     * @return
     */
    @Override
    public ArticleVo findArticleById(Long id) {
        /**
         * 1. 根据id查询文章信息
         * 2. 根据bodyId和categoryId查询内容和类比的信息
         */
        Article article = articleMapper.selectById(id);
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);

        /*文章的标签信息*/
        List<TagVo> tags = tagService.findTagsByArticleId(article.getId());
        articleVo.setTags(tags);

        /*文章的作者信息*/
        SysUser user = userService.findUserById(article.getAuthorId());
        articleVo.setAuthor(user);

        /*文章内容信息*/
        Long bodyId = article.getBodyId();
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        BeanUtils.copyProperties(articleBody, articleBodyVo);
        articleVo.setBody(articleBodyVo);

        /*标签信息*/
        Long categoryId = article.getCategoryId();
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        articleVo.setCategoryVo(categoryVo);

        /**
         * 查看完文章 阅读数需要加1
         *      如果更新出问题 不能影响文章的其余操作
         * 可以把更新操作放到线程池中 这样就和主线程无关了
         */
        threadService.updateReadView(article);
        return articleVo;
    }

    /**
     * 发表文章
     *
     * @param param
     * @return
     */
    @Override
    @Transactional // 这里需不需要开启事务呢
    public ArticleVo publish(ArticleParam param) {
        SysUser user = ThreadLocalUtil.get();

        // 文章的基本信息
        Article article = new Article();
        article.setCommentCounts(0);
        article.setCreateDate(new Date());
        article.setModifiedDate(new Date());
        article.setSummary(param.getSummary());
        article.setTitle(param.getTitle());
        article.setViewCounts(0);
        article.setWeight(0);
        article.setAuthorId(user.getId());
        article.setCategoryId(param.getCategory().getId());
        articleMapper.insert(article); // 会给article返回一个Id? 会的

        Long articleId = article.getId();

        // tags标签 数据库表ms_article_tag
        List<TagVo> tags = param.getTags();
        for (TagVo tagVo : tags) {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setTagId(tagVo.getId());
            articleTag.setArticleId(articleId);
            articleTagService.save(articleTag);
        }

        // 数据库ms_article_body表
        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(param.getBody().getContent());
        articleBody.setContentHtml(param.getBody().getContentHtml());
        articleBody.setArticleId(articleId);
        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        return articleVo;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVos = new ArrayList<>();
        for (Article record : records) {
            ArticleVo articleVo = new ArticleVo();
            BeanUtils.copyProperties(record, articleVo);

            /*文章的标签信息*/
            if (isTag) {
                List<TagVo> tags = tagService.findTagsByArticleId(record.getId());
                articleVo.setTags(tags);
            }

            /*文章的作者信息*/
            if (isAuthor) {
                SysUser user = userService.findUserById(record.getAuthorId());
                articleVo.setAuthor(user);
            }
            articleVos.add(articleVo);
        }
        return articleVos;
    }
}
