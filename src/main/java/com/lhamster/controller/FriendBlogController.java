package com.lhamster.controller;

import com.lhamster.domain.Audience;
import com.lhamster.domain.BlogFriendblog;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import com.lhamster.service.FriendBlogService;
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
 * @date 2020/7/23
 */
@Slf4j
@RestController
public class FriendBlogController {
    @Autowired
    private Audience audience;

    @Autowired
    private FriendBlogService friendBlogService;

    /**
     * 前台友链列表
     *
     * @return
     */
    @GetMapping("/friendBlog")
    public Result<List<BlogFriendblog>> friendBlogList() {
        return new Result<>(ResultCode.SUCCESS, friendBlogService.selectPassed());
    }

    /**
     * 后台友链列表
     *
     * @param id     友链id
     * @param status 友链状态（待审核0|未通过1|已通过2）
     * @param my     若要查询当前登录用户的友链传true
     * @return
     */
    @GetMapping("/friend-blog")
    public Result<List<BlogFriendblog>> friend_blog(QueryVo vo, Integer id, Integer status, @RequestParam(value = "my", defaultValue = "false") Boolean my, HttpServletRequest request) {
        if (StringUtils.isEmpty(vo.getPageNum()) || StringUtils.isEmpty(vo.getPageSize())) {
            throw new ResultException(ResultCode.EMPTY);
        }
        Integer userId = null;
        if (my) {
            // 获取用户id
            userId = Integer.valueOf(JwtTokenUtil.getUserId(request.getHeader("lhamster_identity_info").substring(9), audience.getBase64Secret()));
        }
        return friendBlogService.selectAll(vo, id, status, userId);
    }

    /**
     * 添加申请友链
     *
     * @param name 网站名称
     * @param url  网站地址
     * @return
     */
    @PostMapping("/friend-blog")
    public Result addFriendBlog(String name, String url, HttpServletRequest request) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(url)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        // 获取用户id
        Integer userId = Integer.valueOf(JwtTokenUtil.getUserId(request.getHeader("lhamster_identity_info").substring(9), audience.getBase64Secret()));
        try {
            // 申请友链
            friendBlogService.addFriendBlog(name, url, userId);
            return new Result(ResultCode.FRIEND_ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.FRIEND_ADD_FAILED);
        }
    }

    /**
     * 审核友链申请
     *
     * @param friendId 友链id|不能为空
     * @param status   友链状态（1未通过审核，2通过审核）|不能为空
     * @param reason   未通过的原因，已通过不用写
     * @return
     */
    @PatchMapping("/friend-blog")
    public Result checkFriendBlog(Integer friendId, Integer status, String reason) {
        if (StringUtils.isEmpty(friendId) || StringUtils.isEmpty(status)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 申请友链
            friendBlogService.checkFriendBlog(friendId, status, reason);
            return new Result(ResultCode.FRIEND_CHECK_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.FRIEND_CHECK_FAILED);
        }
    }

    /**
     * 更新友链
     *
     * @param friendId 友链id
     * @param name     友链网站名称
     * @param url      友链网站地址
     * @return
     */
    @PutMapping("/friend-blog")
    public Result updateFriendBlog(Integer friendId, String name, String url) {
        if (StringUtils.isEmpty(friendId) || StringUtils.isEmpty(name) || StringUtils.isEmpty(url)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 修改友链
            friendBlogService.updateFrientBlog(friendId, name, url);
            return new Result(ResultCode.FRIEND_UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.FRIEND_UPDATE_FAILED);
        }
    }

    /**
     * 删除友链
     *
     * @param id 友链id
     * @return
     */
    @DeleteMapping("/friend-blog")
    public Result deleteFriendBlog(Integer id) {
        if (StringUtils.isEmpty(id)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        try {
            // 删除友链
            friendBlogService.deleteFriendBlog(id);
            return new Result(ResultCode.FRIEND_DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.FRIEND_DELETE_FAILED);
        }
    }
}
