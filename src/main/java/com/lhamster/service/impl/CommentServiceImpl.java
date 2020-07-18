package com.lhamster.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhamster.domain.BlogComment;
import com.lhamster.domain.BlogUser;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.mapper.BlogCommentMapper;
import com.lhamster.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    @Autowired
    private BlogCommentMapper blogCommentMapper;

    @Override
    public List<BlogComment> selectAll(Integer articleId) {
        return blogCommentMapper.selectAll(articleId);
    }

    @Override
    public Result<List<BlogComment>> selectBack(QueryVo vo, String comContent, String nickName) {
        Page<Object> page = PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<BlogComment> blogCommentList = blogCommentMapper.queryBack(comContent, nickName);
        return new Result<>(ResultCode.SUCCESS, blogCommentList, (int) page.getTotal());
    }

    @Override
    public List<BlogComment> selectById(Integer userId) {
        return blogCommentMapper.selectByPrimaryKey(userId);
    }

    @Override
    public void setLike(Integer comId, Boolean status) {
        BlogComment blogComment = blogCommentMapper.selectSingle(comId);
        Integer likecount = blogComment.getComLikecount();
        if (status) {
            // 点赞
            likecount++;
        } else {
            // 取消点赞
            likecount--;
        }
        // 点赞
        blogCommentMapper.setLikeCount(comId, likecount);
    }

    @Override
    public void publishComment(String comContent, Integer articleId, Integer parentId, Integer targetId, Integer userId) {
        BlogComment comment = new BlogComment();
        // 文章id
        if (!StringUtils.isEmpty(articleId)) {
            comment.setComArticleId(articleId);
        } else { // 父id和目标id
            BlogUser user = new BlogUser();
            user.setUId(targetId);
            comment.setComComId(parentId);
            comment.setTargetUser(user);
        }
        comment.setComContent(comContent);
        comment.setComComtime(new Date());
        comment.setComLikecount(0);
        BlogUser blogUser = new BlogUser();
        blogUser.setUId(userId);
        comment.setBlogUser(blogUser);
        log.info(comment.toString());
        // 插入数据库
        blogCommentMapper.insert(comment);
    }

    @Override
    public void deleteComment(Integer id) {
        // 查询评论
        BlogComment blogComment = blogCommentMapper.selectSingle(id);
        // 该评论是否是一级评论
        if (!StringUtils.isEmpty(blogComment.getComArticleId())) {
            // 是，查询该评论子评论
            List<BlogComment> blogComments = blogCommentMapper.selectByParentId(blogComment.getComId());
            // 删除该评论子评论
            for (BlogComment comment : blogComments) {
                blogCommentMapper.deleteByPrimaryKey(comment.getComId());
            }
        }
        blogCommentMapper.deleteByPrimaryKey(blogComment.getComId());
    }
}
