package com.lhamster.mapper;

import com.lhamster.domain.BlogArticle;
import com.lhamster.domain.request.QueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogArticleMapper {
    int deleteByPrimaryKey(Integer aId);

    int insert(BlogArticle record);

    BlogArticle selectByPrimaryKey(Integer aId);

    List<BlogArticle> selectAll(QueryVo vo);

    void updateByPrimaryKey(BlogArticle article);

    /*修改文章状态*/
    void setArticleStatus(@Param("id") Integer id, @Param("status") Boolean status);

    /*点赞/取消点赞*/
    void setLikeCount(@Param("id") Integer id, @Param("aLikecount") Integer aLikecount);

    void setVisitCount(@Param("id") Integer id, @Param("aVisitcount") Integer aVisitcount);
}