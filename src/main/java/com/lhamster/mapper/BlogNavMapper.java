package com.lhamster.mapper;

import com.lhamster.domain.BlogNav;
import java.util.List;

public interface BlogNavMapper {
    int deleteByPrimaryKey(Integer navId);

    int insert(BlogNav record);

    BlogNav selectByPrimaryKey(Integer navId);

    List<BlogNav> selectAll();

    int updateByPrimaryKey(BlogNav record);
}