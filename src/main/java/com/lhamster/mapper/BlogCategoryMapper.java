package com.lhamster.mapper;

import com.lhamster.domain.BlogCategory;
import java.util.List;

public interface BlogCategoryMapper {
    int deleteByPrimaryKey(Integer cateId);

    int insert(BlogCategory record);

    BlogCategory selectByPrimaryKey(Integer cateId);

    List<BlogCategory> selectAll();

    int updateByPrimaryKey(BlogCategory record);
}