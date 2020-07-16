package com.lhamster.service.impl;

import com.lhamster.domain.BlogArticle;
import com.lhamster.mapper.BlogArticleMapper;
import com.lhamster.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private BlogArticleMapper blogArticleMapper;

    @Override
    public void insertArticle(BlogArticle article) {
        blogArticleMapper.insert(article);
    }
}
