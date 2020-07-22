package com.lhamster.service.impl;

import com.lhamster.domain.BlogBook;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.mapper.BlogBookMapper;
import com.lhamster.service.BookService;
import com.lhamster.util.TencentCOSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/22
 */
@Slf4j
@Service
@Transactional
public class BookServiceImpl implements BookService {
    @Autowired
    private BlogBookMapper blogBookMapper;

    @Override
    @Cacheable(cacheNames = "book", key = "#root.targetClass")
    public Result<List<BlogBook>> book() {
        return new Result<>(ResultCode.SUCCESS, blogBookMapper.selectAll());
    }

    @Override
    @CacheEvict(cacheNames = "book", key = "#root.targetClass")
    public void addBook(String name, String des, String picUrl) {
        BlogBook book = new BlogBook(null, name, des, picUrl, new Date());
        blogBookMapper.insert(book);
    }

    @Override
    @Cacheable(cacheNames = "book", key = "#id")
    public BlogBook queryBook(Integer id) {
        return blogBookMapper.selectByPrimaryKey(id);
    }

    @Override
    @CachePut(cacheNames = "book", key = "#id")
    @CacheEvict(cacheNames = "book", key = "#root.targetClass")
    public BlogBook updateBook(Integer id, String name, String des, String newPicUrl) {
        BlogBook book = blogBookMapper.selectByPrimaryKey(id);
        if (!StringUtils.isEmpty(name)) {
            book.setBName(name);
        }
        if (!StringUtils.isEmpty(des)) {
            book.setBDiscrible(des);
        }
        if (!StringUtils.isEmpty(newPicUrl)) {
            /*删除旧照片*/
            String bPicture = book.getBPicture();
            if (!StringUtils.isEmpty(bPicture)) {
                TencentCOSUtil.deletefile("bookPicture" + bPicture.substring(bPicture.lastIndexOf("/")));
            }
            book.setBPicture(newPicUrl);
        }
        // 添加到数据库
        blogBookMapper.updateByPrimaryKey(book);
        return book;
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "book", key = "#id"),
                    @CacheEvict(cacheNames = "book", key = "#root.targetClass")
            }
    )
    public void deleteBook(Integer id) {
        BlogBook blogBook = blogBookMapper.selectByPrimaryKey(id);
        /*删除封面图片*/
        if (!StringUtils.isEmpty(blogBook.getBPicture())) {
            TencentCOSUtil.deletefile("bookPicture" + blogBook.getBPicture().substring(blogBook.getBPicture().lastIndexOf("/")));
        }
        /*删除书籍信息*/
        blogBookMapper.deleteByPrimaryKey(id);
    }
}
