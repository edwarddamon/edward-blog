package com.lhamster.service.impl;

import com.lhamster.domain.BlogCategory;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.mapper.BlogCategoryMapper;
import com.lhamster.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private BlogCategoryMapper blogCategoryMapper;

    @Override
    @Cacheable(cacheNames = "category", key = "#root.targetClass")
    public Result<List<BlogCategory>> queryAllCategory() {
        return new Result<>(ResultCode.SUCCESS, blogCategoryMapper.selectAll());
    }

    @Override
    /*删除分类列表的缓存*/
    @CacheEvict(cacheNames = "category", key = "#root.targetClass")
    public void insertCategory(String name) {
        BlogCategory category = new BlogCategory();
        category.setCateName(name);
        blogCategoryMapper.insert(category);
    }

    @Override
    public BlogCategory selectOne(String name) {
        return blogCategoryMapper.selectByName(name);
    }

    @Override
    /*删除分类列表的缓存*/
    @CacheEvict(cacheNames = "category", key = "#root.targetClass")
    public void updateCategory(BlogCategory blogCategory) {
        blogCategoryMapper.updateByPrimaryKey(blogCategory);
    }

    @Override
    /*删除分类列表的缓存*/
    @CacheEvict(cacheNames = "category", key = "#root.targetClass")
    public void deleteCategory(Integer id) {
        blogCategoryMapper.deleteByPrimaryKey(id);
    }
}
