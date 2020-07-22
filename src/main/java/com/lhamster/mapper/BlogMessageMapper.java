package com.lhamster.mapper;

import com.lhamster.domain.BlogMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogMessageMapper {
    int deleteByPrimaryKey(Integer mesId);

    int insert(BlogMessage record);

    BlogMessage selectByPrimaryKey(Integer mesId);

    List<BlogMessage> selectAll(@Param("top") Boolean top, @Param("like") Boolean like, @Param("content") String content);

    int updateByPrimaryKey(BlogMessage record);

    /*根据父id查询当前留言*/
    List<BlogMessage> selectByParentId(Integer parentId);

    /*根据用户id查询留言*/
    List<BlogMessage> selectByUserId(int parseInt);

    /*留言置顶*/
    void setTop(@Param("id") Integer id, @Param("top") Boolean top);
}