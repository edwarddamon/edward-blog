package com.lhamster.service;

import com.lhamster.domain.BlogAdvice;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;

import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/24
 */
public interface AdviceService {
    /*建议列表*/
    Result<List<BlogAdvice>> advice(QueryVo vo, Integer userId);

    /*添加建议*/
    void addAdvice(String title, String content, Integer userId);

    /*删除建议*/
    void deleteAdvice(Integer id);
}
