package com.lhamster.service;

import com.lhamster.domain.BlogBug;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;

import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/24
 */
public interface BugService {
    /*bug列表*/
    Result<List<BlogBug>> bugs(QueryVo vo, Integer userId);

    /*添加bug反馈*/
    void addBug(String content, Integer userId);

    /*删除bug反馈*/
    void deleteBug(Integer id);
}
