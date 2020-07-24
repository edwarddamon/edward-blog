package com.lhamster.service.impl;

import com.lhamster.domain.BlogTag;
import com.lhamster.mapper.BlogNavMapper;
import com.lhamster.mapper.BlogTagMapper;
import com.lhamster.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/24
 */
@Slf4j
@Service
@Transactional
public class TagServiceImpl implements TagService {
    @Autowired
    private BlogTagMapper blogTagMapper;

    @Override
    @Cacheable(cacheNames = "tag", key = "#root.targetClass")
    public List<BlogTag> tags() {
        return blogTagMapper.selectAll();
    }

    @Override
    public BlogTag tag(Integer id) {
        return blogTagMapper.selectByPrimaryKey(id);
    }

    @Override
    @CacheEvict(cacheNames = "tag", key = "#root.targetClass")
    public void addTag(String content) {
        BlogTag blogTag = new BlogTag(null, content);
        blogTagMapper.insert(blogTag);
    }

    @Override
    @CacheEvict(cacheNames = "tag", key = "#root.targetClass")
    public void updateTag(Integer id, String content) {
        BlogTag tag = blogTagMapper.selectByPrimaryKey(id);
        tag.setTagContent(content);
        // 更新
        blogTagMapper.updateByPrimaryKey(tag);
    }

    @Override
    @CacheEvict(cacheNames = "tag", key = "#root.targetClass")
    public void deleteTag(Integer id) {
        blogTagMapper.deleteByPrimaryKey(id);
    }
}
