package com.lyj.vblog.service;

import com.lyj.vblog.pojo.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyj.vblog.vo.TagVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
public interface ITagService extends IService<Tag> {

    /**
     * 根据文章id查询文章的tags
     * @param id 文章id
     * @return
     */
    List<TagVo> findTagsByArticleId(Long id);

    List<Tag> findHotTags(int limit);

    /**
     * 所有标签
     * @return
     */
    List<TagVo> findAll();

    /**
     * 根据id查询标签信息
     * @param id
     * @return
     */
    TagVo findTagDetail(Long id);
}
