package com.lyj.vblog.service;

import com.lyj.vblog.pojo.ArticleTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
public interface IArticleTagService extends IService<ArticleTag> {

    /**
     * 根据tag_id查找数据
     * @return
     * @param tagId
     */
    List<ArticleTag> findByTagId(Long tagId);
}
