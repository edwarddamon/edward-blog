package com.lhamster.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhamster.domain.BlogComment;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.mapper.BlogCommentMapper;
import com.lhamster.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    @Autowired
    private BlogCommentMapper blogCommentMapper;

    @Override
    @Cacheable(cacheNames = "comment")
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
        blogCommentMapper.setLikeCount(comId,likecount);
    }
}
