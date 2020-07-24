package com.lhamster.service;

import com.lhamster.domain.BlogFriendblog;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;

import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/23
 */
public interface FriendBlogService {
    /*前台查询已通过审核的友链*/
    List<BlogFriendblog> selectPassed();

    /*后台友链列表*/
    Result<List<BlogFriendblog>> selectAll(QueryVo vo, Integer id, Integer status, Integer userId);

    /*添加申请友链*/
    void addFriendBlog(String name, String url, Integer userId);

    /*审核友链*/
    BlogFriendblog checkFriendBlog(Integer friendId, Integer status, String reason);

    /*更新友链*/
    BlogFriendblog updateFrientBlog(Integer friendId, String name, String url);

    /*删除友链*/
    void deleteFriendBlog(Integer id);
}
