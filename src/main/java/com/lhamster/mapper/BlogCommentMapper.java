package com.lhamster.mapper;

import com.lhamster.domain.BlogComment;
import java.util.List;

public interface BlogCommentMapper {
    int deleteByPrimaryKey(Integer comId);

    int insert(BlogComment record);

    BlogComment selectByPrimaryKey(Integer comId);

    List<BlogComment> selectAll();

    int updateByPrimaryKey(BlogComment record);
}