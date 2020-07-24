package com.lhamster.mapper;

import com.lhamster.domain.BlogAdvice;

import java.util.List;

public interface BlogAdviceMapper {
    int deleteByPrimaryKey(Integer adId);

    int insert(BlogAdvice record);

    BlogAdvice selectByPrimaryKey(Integer adId);

    List<BlogAdvice> selectAll(Integer userId);

    int updateByPrimaryKey(BlogAdvice record);
}