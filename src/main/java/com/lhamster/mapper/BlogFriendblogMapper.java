package com.lhamster.mapper;

import com.lhamster.domain.BlogFriendblog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogFriendblogMapper {
    int deleteByPrimaryKey(Integer fId);

    int insert(BlogFriendblog record);

    BlogFriendblog selectByPrimaryKey(Integer fId);

    List<BlogFriendblog> selectAll();

    int updateByPrimaryKey(BlogFriendblog record);

    List<BlogFriendblog> queryAll(@Param("id") Integer id, @Param("status") Integer status, @Param("userId") Integer userId);
}