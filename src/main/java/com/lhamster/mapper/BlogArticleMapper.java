package com.lhamster.mapper;

import com.lhamster.domain.BlogArticle;
import java.util.List;

public interface BlogArticleMapper {
    int deleteByPrimaryKey(Integer aId);

    int insert(BlogArticle record);

    BlogArticle selectByPrimaryKey(Integer aId);

    List<BlogArticle> selectAll();

    int updateByPrimaryKey(BlogArticle record);
}