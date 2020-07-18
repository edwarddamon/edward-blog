package com.lhamster.controller;

import com.lhamster.domain.Audience;
import com.lhamster.domain.BlogComment;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import com.lhamster.service.CommentService;
import com.lhamster.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
public class CommentController {
    @Autowired
    private Audience audience;

    @Autowired
    private CommentService commentService;

    /**
     * 评论列表
     *
     * @param articleId
     * @return
     */
    @GetMapping("/comments")
    public Result<List<BlogComment>> comments(Integer articleId) {
        if (StringUtils.isEmpty(articleId)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        return new Result<>(ResultCode.SUCCESS, commentService.selectAll(articleId));
    }

    /**
     * 评论点赞/取消点赞
     *
     * @param comId
     * @param status
     * @return
     */
    @PutMapping("/comments")
    public Result setLike(Integer comId, Boolean status) {
        if (StringUtils.isEmpty(comId) || StringUtils.isEmpty(status)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 点赞
            commentService.setLike(comId, status);
            return new Result(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.FAIL);
        }
    }

    /**
     * 后台评论列表
     *
     * @param vo
     * @param comContent
     */
    @GetMapping("/comment")
    public Result<List<BlogComment>> comment(QueryVo vo, String comContent, String nickName) {
        if (StringUtils.isEmpty(vo.getPageNum()) || StringUtils.isEmpty(vo.getPageSize())) {
            throw new ResultException(ResultCode.EMPTY);
        }
        return commentService.selectBack(vo, comContent, nickName);
    }

    /**
     * 查询当前登录用户的评论
     *
     * @return
     */
    @GetMapping("/comment-me")
    public Result<List<BlogComment>> myComment(HttpServletRequest request) {
        // 获取用户id
        String userId = JwtTokenUtil.getUserId(request.getHeader("lhamster_identity_info").substring(9), audience.getBase64Secret());
        return new Result<>(ResultCode.SUCCESS, commentService.selectById(Integer.valueOf(userId)));
    }

    /**
     * 发表评论
     *
     * @param comContent 评论内容
     * @param articleId  文章id（1）
     * @param parentId   父评论id（2）
     * @param targetId   目标用户id（2）
     * @return
     */
    @PostMapping("/comment")
    public Result insertComment(String comContent, Integer articleId, Integer parentId, Integer targetId, HttpServletRequest request) {
        if (StringUtils.isEmpty(comContent)) {
            throw new ResultException(ResultCode.COMMENT_PUBLISH_EMPTY);
        }
        // 获取用户id
        String userId = JwtTokenUtil.getUserId(request.getHeader("lhamster_identity_info").substring(9), audience.getBase64Secret());
        try {
            // 发布评论
            commentService.publishComment(comContent, articleId, parentId, targetId, Integer.parseInt(userId));
            return new Result(ResultCode.COMMENT_PUBLISH_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.COMMENT_PUBLISH_FAILED);
        }
    }

    /**
     * 删除评论
     * 说明：该评论及其子评论都删除了
     *
     * @param id
     * @return
     */
    @DeleteMapping("/comment")
    public Result deleteCom(Integer id) {
        if (StringUtils.isEmpty(id)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 删除评论
            commentService.deleteComment(id);
            return new Result(ResultCode.COMMENT_DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.COMMENT_DELETE_FAILED);
        }
    }
}