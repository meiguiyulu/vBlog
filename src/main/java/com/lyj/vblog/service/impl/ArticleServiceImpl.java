package com.lyj.vblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyj.vblog.dos.Archives;
import com.lyj.vblog.mapper.ArticleBodyMapper;
import com.lyj.vblog.mapper.CategoryMapper;
import com.lyj.vblog.pojo.Article;
import com.lyj.vblog.mapper.ArticleMapper;
import com.lyj.vblog.pojo.ArticleBody;
import com.lyj.vblog.pojo.Category;
import com.lyj.vblog.pojo.SysUser;
import com.lyj.vblog.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyj.vblog.service.ISysUserService;
import com.lyj.vblog.service.ITagService;
import com.lyj.vblog.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NamingEnumeration;
import java.util.ArrayList;
import java.util.Collections;
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
        Article article = articleMapper.selectById(id);
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);

        /*文章的标签信息*/
        List<TagVo> tags = tagService.findTagsByArticleId(article.getId());
        articleVo.setTags(tags);

        /*文章的作者信息*/
        SysUser user = userService.findUserById(article.getAuthorId());
        articleVo.setAuthor(user.getNickname());

        /*文章内容信息*/
        Long bodyId = article.getBodyId();
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        BeanUtils.copyProperties(articleBody, articleBodyVo);
        articleVo.setBody(articleBodyVo);

        /*标签信息*/
        Integer categoryId = article.getCategoryId();
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        articleVo.setCategoryVo(categoryVo);
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
                articleVo.setAuthor(user.getNickname());
            }
            articleVos.add(articleVo);
        }
        return articleVos;
    }
}
