package com.lyj.vblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyj.vblog.pojo.Article;
import com.lyj.vblog.mapper.ArticleMapper;
import com.lyj.vblog.pojo.SysUser;
import com.lyj.vblog.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyj.vblog.service.ISysUserService;
import com.lyj.vblog.service.ITagService;
import com.lyj.vblog.vo.ArticleVo;
import com.lyj.vblog.vo.PageParams;
import com.lyj.vblog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<ArticleVo> articleVos = new ArrayList<>();

        articleVos = copyList(articleVos, records, true, true);
        return articleVos;
    }

    private List<ArticleVo> copyList(List<ArticleVo> articleVos, List<Article> records, boolean isTag, boolean isAuthor) {
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
