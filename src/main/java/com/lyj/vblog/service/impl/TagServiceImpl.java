package com.lyj.vblog.service.impl;

import com.lyj.vblog.pojo.Tag;
import com.lyj.vblog.mapper.TagMapper;
import com.lyj.vblog.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyj.vblog.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long id) {
        return tagMapper.findTagsByArticleId(id);
    }

    @Override
    public List<Tag> findHotTags(int limit) {
        /*这里可以直接一条sql语句多表查询也可
        * select
        from ms_tag
        where id in (
            select tag_id
            from (select tag_id
                  from ms_article_tag
                  group by tag_id
                  order by count(tag_id) desc
                  limit 6)
                     as t
        );
        */

        List<Long> hotTagIds = tagMapper.findHotTagsIds(limit);
        if (hotTagIds.isEmpty()) {
            return Collections.emptyList();
        }
//        System.out.println("hotTagIds: " + hotTagIds);

        List<Tag> tags = tagMapper.selectBatchIds(hotTagIds);
        return tags;
    }
}
