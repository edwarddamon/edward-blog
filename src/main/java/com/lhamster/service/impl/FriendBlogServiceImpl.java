package com.lhamster.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhamster.domain.BlogFriendblog;
import com.lhamster.domain.BlogUser;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.mapper.BlogFriendblogMapper;
import com.lhamster.service.FriendBlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/23
 */
@Slf4j
@Service
@Transactional
public class FriendBlogServiceImpl implements FriendBlogService {
    @Autowired
    private BlogFriendblogMapper blogFriendblogMapper;

    @Override
    @Cacheable(cacheNames = "friendBlog", key = "#root.targetClass")
    public List<BlogFriendblog> selectPassed() {
        return blogFriendblogMapper.selectAll();
    }

    @Override
    public Result<List<BlogFriendblog>> selectAll(QueryVo vo, Integer id, Integer status, Integer userId) {
        Page<Object> page = PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        return new Result<>(ResultCode.SUCCESS, blogFriendblogMapper.queryAll(id, status, userId), (int) page.getTotal());
    }

    @Override
    @CacheEvict(cacheNames = "friendBlog", key = "#root.targetClass")
    public void addFriendBlog(String name, String url, Integer userId) {
        BlogFriendblog friendblog = new BlogFriendblog();
        friendblog.setFName(name);
        friendblog.setFUrl(url);
        friendblog.setFStatus(0);
        friendblog.setFFriendblogtime(new Date());
        BlogUser blogUser = new BlogUser();
        blogUser.setUId(userId);
        friendblog.setUser(blogUser);
        // 新增友链申请
        blogFriendblogMapper.insert(friendblog);
    }

    @Override
    @CacheEvict(cacheNames = "friendBlog", key = "#root.targetClass")
    public BlogFriendblog checkFriendBlog(Integer friendId, Integer status, String reason) {
        BlogFriendblog friendblog = blogFriendblogMapper.selectByPrimaryKey(friendId);
        if (status.equals(1)) {
            reason = "很遗憾，您申请的友链[" + friendblog.getFName() + "]没有通过审核；原因：" + reason;
        }
        if (status.equals(2)) {
            reason = "恭喜您，您申请的友链[" + friendblog.getFName() + "]已通过审核";
        }
        friendblog.setFBackinfo(reason);
        friendblog.setFStatus(status);
        // 更新
        blogFriendblogMapper.updateByPrimaryKey(friendblog);
        return friendblog;
    }

    @Override
    @CacheEvict(cacheNames = "friendBlog", key = "#root.targetClass")
    public BlogFriendblog updateFrientBlog(Integer friendId, String name, String url) {
        BlogFriendblog friendblog = blogFriendblogMapper.selectByPrimaryKey(friendId);
        friendblog.setFName(name);
        friendblog.setFUrl(url);
        friendblog.setFStatus(0);
        friendblog.setFBackinfo("友链更新成功，审核中...");
        // 更新
        blogFriendblogMapper.updateByPrimaryKey(friendblog);
        return friendblog;
    }

    @Override
    @CacheEvict(cacheNames = "friendBlog", key = "#root.targetClass")
    public void deleteFriendBlog(Integer id) {
        blogFriendblogMapper.deleteByPrimaryKey(id);
    }
}