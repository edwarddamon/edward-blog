package com.lhamster.mapper;

import com.lhamster.domain.BlogBug;
import java.util.List;

public interface BlogBugMapper {
    int deleteByPrimaryKey(Integer bugId);

    int insert(BlogBug record);

    BlogBug selectByPrimaryKey(Integer bugId);

    List<BlogBug> selectAll();

    int updateByPrimaryKey(BlogBug record);
}