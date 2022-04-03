package com.lyj.vblog.mapper;

import com.lyj.vblog.pojo.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyj.vblog.vo.TagVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    List<TagVo> findTagsByArticleId(Long id);

    List<Long> findHotTagsIds(int limit);
}
