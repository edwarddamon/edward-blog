package com.lhamster.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhamster.domain.BlogAdvice;
import com.lhamster.domain.BlogUser;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.mapper.BlogAdviceMapper;
import com.lhamster.service.AdviceService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
@Transactional
public class AdviceServiceImpl implements AdviceService {
    @Autowired
    private BlogAdviceMapper blogAdviceMapper;

    @Override
    public Result<List<BlogAdvice>> advice(QueryVo vo, Integer userId) {
        Page<Object> page = PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        return new Result<>(ResultCode.SUCCESS, blogAdviceMapper.selectAll(userId), (int) page.getTotal());
    }

    @Override
    public void addAdvice(String title, String content, Integer userId) {
        BlogUser blogUser = new BlogUser();
        blogUser.setUId(userId);
        BlogAdvice advice = new BlogAdvice(null, title, new Date(), content, blogUser);
        blogAdviceMapper.insert(advice);
    }

    @Override
    public void deleteAdvice(Integer id) {
        blogAdviceMapper.deleteByPrimaryKey(id);
    }
}
