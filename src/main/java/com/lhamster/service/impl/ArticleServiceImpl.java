package com.lhamster.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhamster.domain.BlogArticle;
import com.lhamster.domain.BlogComment;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.mapper.BlogArticleMapper;
import com.lhamster.service.ArticleService;
import com.lhamster.service.CommentService;
import com.lhamster.util.TencentCOSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private BlogArticleMapper blogArticleMapper;

    @Autowired
    private CommentService commentService;

    @Override
    public void insertArticle(BlogArticle article) {
        blogArticleMapper.insert(article);
    }

    @Override
    @Cacheable(cacheNames = "article")
    public BlogArticle queryArticle(Integer id) {
        return blogArticleMapper.selectByPrimaryKey(id);
    }

    @Override
    public Result<List<BlogArticle>> queryMoreArticle(QueryVo vo) {
        Page<Object> page = PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        return new Result<>(ResultCode.SUCCESS, blogArticleMapper.selectAll(vo), (int) page.getTotal());
    }

    @Override
    @CachePut(cacheNames = "article", key = "#id")
    public BlogArticle updateArticle(Integer id, String title, String content, String pictures) {
        List<String> oldPic = new ArrayList<>();
        List<String> newPic = new ArrayList<>();
        String pic = pictures;
        // 查询该文章原先的内容
        BlogArticle article = blogArticleMapper.selectByPrimaryKey(id);
        // 删除腾讯云上文章中丢弃的图片
        String oldPicUrl = article.getAPicture();
        while (!StringUtils.isEmpty(oldPicUrl)) {
            if (!oldPicUrl.contains(",")) {
                oldPic.add(oldPicUrl);
                break;
            }
            oldPic.add(oldPicUrl.substring(0, oldPicUrl.indexOf(",")));
            oldPicUrl = oldPicUrl.substring(oldPicUrl.indexOf(",") + 1);
        }
        while (!StringUtils.isEmpty(pictures)) {
            if (!pictures.contains(",")) {
                newPic.add(pictures);
                break;
            }
            newPic.add(pictures.substring(0, pictures.indexOf(",")));
            pictures = pictures.substring(pictures.indexOf(",") + 1);
        }
        for (String odlUrl : oldPic) {
            if (!newPic.contains(odlUrl)) {
                TencentCOSUtil.deletefile("articlePicture/" + odlUrl.substring(odlUrl.lastIndexOf("/") + 1));
            }
        }
        // 设置新内容
        article.setATitle(title);
        article.setAContent(content);
        article.setAPicture(pic);
        log.info(article.toString());
        // 更新
        blogArticleMapper.updateByPrimaryKey(article);
        return article;
    }

    @Override
    @CachePut(cacheNames = "article", key = "#id")
    public BlogArticle deleteArticle(Integer id, Boolean status) {
        BlogArticle blogArticle = blogArticleMapper.selectByPrimaryKey(id);
        blogArticleMapper.setArticleStatus(id, status);
        blogArticle.setAStatus(status);
        return blogArticle;
    }

    @Override
    @CachePut(cacheNames = "article", key = "#id")
    public BlogArticle setLike(Integer id, Boolean like) {
        BlogArticle blogArticle = blogArticleMapper.selectByPrimaryKey(id);
        if (like) {
            // 点赞
            blogArticle.setALikecount(blogArticle.getALikecount() + 1);
        } else {
            // 取消点赞
            blogArticle.setALikecount(blogArticle.getALikecount() - 1);
        }
        blogArticleMapper.setLikeCount(id, blogArticle.getALikecount());
        return blogArticle;
    }

    @Override
    @CachePut(cacheNames = "article")
    public BlogArticle addBlogVistCount(Integer id) {
        BlogArticle blogArticle = blogArticleMapper.selectByPrimaryKey(id);
        blogArticle.setAVisitcount(blogArticle.getAVisitcount() + 1);
        // 插入数据库
        blogArticleMapper.setVisitCount(id, blogArticle.getAVisitcount());
        return blogArticle;
    }

    @Override
    @CacheEvict(cacheNames = "article")
    public void delete(Integer articleId) {
        // 删除腾讯云该文章的图片
        List<String> newPic = new ArrayList<>();
        BlogArticle blogArticle = blogArticleMapper.selectByPrimaryKey(articleId);
        String pictures = blogArticle.getAPicture();
        while (!StringUtils.isEmpty(pictures)) {
            if (!pictures.contains(",")) {
                newPic.add(pictures);
                break;
            }
            newPic.add(pictures.substring(0, pictures.indexOf(",")));
            pictures = pictures.substring(pictures.indexOf(",") + 1);
        }
        log.info(newPic.toString());
        for (String s : newPic) {
            TencentCOSUtil.deletefile("articlePicture" + s.substring(s.lastIndexOf("/")));
        }
        // 删除博客的评论
        List<BlogComment> blogComments = commentService.selectAll(articleId);
        for (BlogComment blogComment : blogComments) {
            commentService.deleteComment(blogComment.getComId());
        }
        // 删除博客文章
        blogArticleMapper.deleteByPrimaryKey(articleId);
    }
}
