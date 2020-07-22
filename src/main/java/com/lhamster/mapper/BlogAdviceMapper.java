package com.lhamster.mapper;

import com.lhamster.domain.BlogAdvice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface BlogAdviceMapper {
    int deleteByPrimaryKey(Integer adId);

    int insert(BlogAdvice record);

    BlogAdvice selectByPrimaryKey(Integer adId);

    List<BlogAdvice> selectAll();

    int updateByPrimaryKey(BlogAdvice record);
}