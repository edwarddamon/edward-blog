package com.lhamster.controller;

import com.lhamster.domain.Audience;
import com.lhamster.domain.BlogMessage;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import com.lhamster.service.MessageService;
import com.lhamster.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/21
 */
@Slf4j
@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private Audience audience;

    /**
     * 前台查询留言
     *
     * @param vo
     * @param top  传true查询置顶留言
     * @param like 传true按照点赞最多逆序排序
     * @return
     */
    @GetMapping("/message")
    public Result<List<BlogMessage>> getMessage(QueryVo vo, Boolean top, Boolean like, String content) {
        if (StringUtils.isEmpty(vo.getPageNum()) || StringUtils.isEmpty(vo.getPageSize())) {
            throw new ResultException(ResultCode.EMPTY);
        }
        return messageService.getMessage(vo, top, like, content);
    }

    /**
     * 点赞/取消点赞
     *
     * @param id   留言id
     * @param like true点赞/false取消点赞
     * @return
     */
    @PostMapping("/message")
    public Result setLike(Integer id, Boolean like) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(like)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 点赞，设置喜欢的人数
            messageService.setLike(id, like);
            return new Result(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.FAIL);
        }
    }

    /**
     * 获取我发布过的留言
     *
     * @param request
     * @return
     */
    @GetMapping("/blog-message")
    public Result<List<BlogMessage>> myMessage(HttpServletRequest request) {
        // 获取用户id
        String userId = JwtTokenUtil.getUserId(request.getHeader("lhamster_identity_info").substring(9), audience.getBase64Secret());
        return new Result<>(ResultCode.SUCCESS, messageService.getMyMessage(Integer.parseInt(userId)));
    }

    /**
     * 发布留言
     *
     * @param content 留言内容，不能为空
     * @param target  目标用户id（A）
     * @param parent  父留言id（A）
     * @param request
     * @return
     */
    @PostMapping("/blog-message")
    public Result addMessage(String content, Integer target, Integer parent, HttpServletRequest request) {
        if (StringUtils.isEmpty(content)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        // 获取用户id
        String userId = JwtTokenUtil.getUserId(request.getHeader("lhamster_identity_info").substring(9), audience.getBase64Secret());
        try {
            // 发布留言
            messageService.addMessage(content, target, parent, userId);
            return new Result(ResultCode.MESSAGE_PUBLISH_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.MESSAGE_PUBLISH_FAILED);
        }
    }

    /**
     * 留言置顶（取消）
     *
     * @param id  留言id
     * @param top true置顶/false取消置顶
     * @return
     */
    @PutMapping("/blog-message")
    public Result setTop(Integer id, Boolean top) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(top)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 留言置顶
            messageService.setTop(id, top);
            return new Result(ResultCode.MESSAGE_TOP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.MESSAGE_TOP_FAILED);
        }
    }

    /**
     * 删除留言
     *
     * @param id 留言id
     * @return
     */
    @DeleteMapping("/blog-message")
    public Result deleteMsg(Integer id, HttpServletRequest request) {
        if (StringUtils.isEmpty(id)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        String type = request.getHeader("type");
        // 获取用户id
        String userId = JwtTokenUtil.getUserId(request.getHeader("lhamster_identity_info").substring(9), audience.getBase64Secret());
        try {
            // 删除留言
            messageService.deleteMsg(id, type, userId);
            return new Result(ResultCode.MESSAGE_DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.MESSAGE_DELETE_FAILED);
        }
    }
}
