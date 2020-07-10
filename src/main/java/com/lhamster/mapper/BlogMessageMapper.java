package com.lhamster.mapper;

import com.lhamster.domain.BlogMessage;
import java.util.List;

public interface BlogMessageMapper {
    int deleteByPrimaryKey(Integer mesId);

    int insert(BlogMessage record);

    BlogMessage selectByPrimaryKey(Integer mesId);

    List<BlogMessage> selectAll();

    int updateByPrimaryKey(BlogMessage record);
}