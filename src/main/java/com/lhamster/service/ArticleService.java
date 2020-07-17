package com.lhamster.service;

import com.lhamster.domain.BlogArticle;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;

import java.util.List;

public interface ArticleService {
    void insertArticle(BlogArticle article);/*插入博客文章*/

    BlogArticle queryArticle(Integer id);/*根据文章id查询文章*/

    Result<List<BlogArticle>> queryMoreArticle(QueryVo vo);/*博客列表*/

    BlogArticle updateArticle(Integer id, String title, String content, String pictures);/*修改文章*/

    BlogArticle deleteArticle(Integer id, Boolean status);/*假删除*/

    BlogArticle setLike(Integer id, Boolean like);/*点赞*/

    BlogArticle addBlogVistCount(Integer id);/*统计博客访问次数*/
}
