package com.lhamster.service;

import com.lhamster.domain.BlogComment;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentService {
    List<BlogComment> selectAll(Integer articleId);/*评论列表*/

    Result<List<BlogComment>> selectBack(@Param("vo") QueryVo vo, @Param("comContent") String comContent, @Param("nickName") String nickName);/*后台评论列表*/

    List<BlogComment> selectById(Integer userId);/*根据用户id查询评论*/

    void setLike(Integer comId, Boolean status);/*点赞*/
}
