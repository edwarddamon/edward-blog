package com.lhamster.mapper;

import com.lhamster.domain.BlogUpdatelog;
import java.util.List;

public interface BlogUpdatelogMapper {
    int deleteByPrimaryKey(Integer upId);

    int insert(BlogUpdatelog record);

    BlogUpdatelog selectByPrimaryKey(Integer upId);

    List<BlogUpdatelog> selectAll();

    int updateByPrimaryKey(BlogUpdatelog record);
}