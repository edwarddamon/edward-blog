package com.lhamster.mapper;

import com.lhamster.domain.BlogCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogCategoryMapper {
    int deleteByPrimaryKey(Integer cateId);

    int insert(BlogCategory record);

    BlogCategory selectByPrimaryKey(Integer cateId);

    List<BlogCategory> selectAll();

    int updateByPrimaryKey(BlogCategory record);

    /*根据名称查询分类*/
    BlogCategory selectByName(@Param("name") String name);
}