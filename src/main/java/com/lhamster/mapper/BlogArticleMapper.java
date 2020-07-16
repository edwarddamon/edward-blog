package com.lhamster.mapper;

import com.lhamster.domain.BlogArticle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogArticleMapper {
    int deleteByPrimaryKey(Integer aId);

    int insert(BlogArticle record);

    BlogArticle selectByPrimaryKey(Integer aId);

    List<BlogArticle> selectAll();

    int updateByPrimaryKey(BlogArticle record);
}