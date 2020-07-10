package com.lhamster.mapper;

import com.lhamster.domain.BlogUser;

import java.util.List;

public interface BlogUserMapper {
    int deleteByPrimaryKey(Integer uId);

    int insert(BlogUser record);

    BlogUser selectByPrimaryKey(Integer uId);

    List<BlogUser> selectAll();

    int updateByPrimaryKey(BlogUser record);
}