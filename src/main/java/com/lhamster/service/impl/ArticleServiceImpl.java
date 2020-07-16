package com.lhamster.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhamster.domain.BlogArticle;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.mapper.BlogArticleMapper;
import com.lhamster.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private BlogArticleMapper blogArticleMapper;

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
}
