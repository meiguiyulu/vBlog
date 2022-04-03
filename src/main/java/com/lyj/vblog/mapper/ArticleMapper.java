package com.lyj.vblog.mapper;

import com.lyj.vblog.dos.Archives;
import com.lyj.vblog.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 文章归档
     * @return
     */
    List<Archives> listArchives();
}
