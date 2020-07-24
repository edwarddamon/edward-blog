package com.lhamster.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhamster.domain.BlogBug;
import com.lhamster.domain.BlogUser;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.mapper.BlogBugMapper;
import com.lhamster.service.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/24
 */
@Service
@Transactional
public class BugServiceImpl implements BugService {
    @Autowired
    private BlogBugMapper blogBugMapper;

    @Override
    public Result<List<BlogBug>> bugs(QueryVo vo, Integer userId) {
        Page<Object> page = PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        return new Result<>(ResultCode.SUCCESS, blogBugMapper.selectAll(userId), (int) page.getTotal());
    }

    @Override
    public void addBug(String content, Integer userId) {
        BlogUser blogUser = new BlogUser();
        blogUser.setUId(userId);
        BlogBug blogBug = new BlogBug(null, new Date(), content, blogUser);
        // 添加
        blogBugMapper.insert(blogBug);
    }

    @Override
    public void deleteBug(Integer id) {
        blogBugMapper.deleteByPrimaryKey(id);
    }
}
