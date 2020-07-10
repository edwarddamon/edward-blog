package com.lhamster.mapper;

import com.lhamster.domain.BlogDiary;
import java.util.List;

public interface BlogDiaryMapper {
    int deleteByPrimaryKey(Integer dId);

    int insert(BlogDiary record);

    BlogDiary selectByPrimaryKey(Integer dId);

    List<BlogDiary> selectAll();

    int updateByPrimaryKey(BlogDiary record);
}