package com.lhamster.mapper;

import com.lhamster.domain.BlogTag;
import java.util.List;

public interface BlogTagMapper {
    int deleteByPrimaryKey(Integer tagId);

    int insert(BlogTag record);

    BlogTag selectByPrimaryKey(Integer tagId);

    List<BlogTag> selectAll();

    int updateByPrimaryKey(BlogTag record);
}