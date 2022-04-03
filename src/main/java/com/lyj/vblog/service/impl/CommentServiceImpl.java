package com.lyj.vblog.service.impl;

import com.lyj.vblog.pojo.Comment;
import com.lyj.vblog.mapper.CommentMapper;
import com.lyj.vblog.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiuYunJie
 * @since 2022-04-02
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
