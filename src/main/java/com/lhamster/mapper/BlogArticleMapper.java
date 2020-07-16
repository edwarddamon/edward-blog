package com.lhamster.mapper;

import com.lhamster.domain.BlogArticle;
import com.lhamster.domain.request.QueryVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogArticleMapper {
    int deleteByPrimaryKey(Integer aId);

    int insert(BlogArticle record);

    BlogArticle selectByPrimaryKey(Integer aId);

    List<BlogArticle> selectAll(QueryVo vo);

    int updateByPrimaryKey(BlogArticle record);
}