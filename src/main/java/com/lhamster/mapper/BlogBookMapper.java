package com.lhamster.mapper;

import com.lhamster.domain.BlogBook;
import java.util.List;

public interface BlogBookMapper {
    int deleteByPrimaryKey(Integer bId);

    int insert(BlogBook record);

    BlogBook selectByPrimaryKey(Integer bId);

    List<BlogBook> selectAll();

    int updateByPrimaryKey(BlogBook record);
}