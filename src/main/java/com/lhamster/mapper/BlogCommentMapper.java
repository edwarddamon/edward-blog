package com.lhamster.mapper;

import com.lhamster.domain.BlogComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogCommentMapper {
    int deleteByPrimaryKey(Integer comId);

    int insert(BlogComment record);

    List<BlogComment> selectByPrimaryKey(Integer userId);

    List<BlogComment> selectAll(Integer articleId);

    int updateByPrimaryKey(BlogComment record);

    /*根据父id查询评论*/
    List<BlogComment> selectByParentId(Integer parentId);

    /*后台评论列表*/
    List<BlogComment> queryBack(@Param("comContent") String comContent, @Param("nickName") String nickName);

    /*查询当个评论*/
    BlogComment selectSingle(Integer comId);

    /*点赞/取消点赞*/
    void setLikeCount(@Param("comId") Integer comId, @Param("likecount") Integer likecount);
}