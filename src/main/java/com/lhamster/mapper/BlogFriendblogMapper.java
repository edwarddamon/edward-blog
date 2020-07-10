package com.lhamster.mapper;

import com.lhamster.domain.BlogFriendblog;
import java.util.List;

public interface BlogFriendblogMapper {
    int deleteByPrimaryKey(Integer fId);

    int insert(BlogFriendblog record);

    BlogFriendblog selectByPrimaryKey(Integer fId);

    List<BlogFriendblog> selectAll();

    int updateByPrimaryKey(BlogFriendblog record);
}