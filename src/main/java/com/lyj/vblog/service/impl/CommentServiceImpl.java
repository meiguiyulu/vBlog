package com.lyj.vblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyj.vblog.params.CommentParam;
import com.lyj.vblog.pojo.Comment;
import com.lyj.vblog.mapper.CommentMapper;
import com.lyj.vblog.pojo.SysUser;
import com.lyj.vblog.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyj.vblog.service.ISysUserService;
import com.lyj.vblog.utils.ThreadLocalUtil;
import com.lyj.vblog.vo.CommentVo;
import com.lyj.vblog.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ISysUserService userService;

    /**
     * 根据文章id查询评论信息
     *
     * @param id
     * @return
     */
    @Override
    public List<CommentVo> commentsByArticleId(Long id) {
        /**
         * 1. 根据文章id，从评论表中查询评论
         * 2. 根据作者的id 查询出评论的用户信息
         * 3. 判断评论的level
         *      3.1 若 level == 1 查询有没有子评论；
         *      若有，则可以根据评论的parent_id进行查询
         */

        QueryWrapper<Comment> wrapper = new QueryWrapper<Comment>()
                .eq("article_id", id)
                .eq("level", 1);
        List<Comment> comments = commentMapper.selectList(wrapper);
        List<CommentVo> commentVoList = copyList(comments);

        return commentVoList;
    }

    /**
     * 新增评论
     *
     * @param param
     * @return
     */
    @Override
    public Boolean addComment(CommentParam param) {
        SysUser user = ThreadLocalUtil.get(); // 当前登录用户
        Comment comment = new Comment();
        comment.setArticleId(param.getArticleId());
        comment.setAuthorId(user.getId());
        comment.setContent(param.getContent());
        comment.setCreateDate(new Date());
        Long parent = param.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        } else {
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        comment.setToUid(param.getToUserId() == null ? 0 : param.getToUserId());
        commentMapper.insert(comment);
        return null;
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVos = new ArrayList<>();
        for (Comment comment : comments) {
            commentVos.add(copy(comment));
        }
        return commentVos;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        // 发表评论的用户信息
        Long authorId = comment.getAuthorId();
        SysUser user = userService.findUserById(authorId);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        commentVo.setAuthor(userVo);

        // 子评论
        Integer level = comment.getLevel();
        if (1 == level) {
            Long id = comment.getId();
            List<CommentVo> children = findCommentsByParentId(id);
            commentVo.setChildren(children);
        }

        if (level > 1) {
            Long toUid = comment.getToUid();
            SysUser userById = userService.findUserById(toUid);
            UserVo userVo1 = new UserVo();
            BeanUtils.copyProperties(userById, userVo1);
            commentVo.setToUser(userVo1);
        }

        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<Comment>()
                .eq("parent_id", id)
                .eq("level", 2);
        List<Comment> comments = commentMapper.selectList(wrapper);

        return copyList(comments);
    }
}
