package com.lhamster.mapper;

import com.lhamster.domain.BlogInform;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogInformMapper {
    int deleteByPrimaryKey(Integer inId);

    int insert(BlogInform record);

    BlogInform selectByPrimaryKey(Integer inId);

    List<BlogInform> selectAll(@Param("read") Boolean read, @Param("userId") int userId);

    int updateByPrimaryKey(Integer informId);

    List<BlogInform> selectByUserId(int userId);
}