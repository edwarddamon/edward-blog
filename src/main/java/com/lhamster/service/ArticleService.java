package com.lhamster.service;

import com.lhamster.domain.BlogArticle;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;

import java.util.List;

public interface ArticleService {
    void insertArticle(BlogArticle article);/*插入博客文章*/

    BlogArticle queryArticle(Integer id);/*根据文章id查询文章*/

    Result<List<BlogArticle>> queryMoreArticle(QueryVo vo);/*博客列表*/
}
