package com.lyj.vblog.service;

import com.lyj.vblog.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyj.vblog.vo.CommentVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
public interface ICommentService extends IService<Comment> {

    /**
     * 根据文章id查询评论
     * @param id
     * @return
     */
    List<CommentVo> commentsByArticleId(Long id);
}
