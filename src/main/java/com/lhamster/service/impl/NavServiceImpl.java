package com.lhamster.service.impl;

import com.lhamster.domain.BlogNav;
import com.lhamster.mapper.BlogNavMapper;
import com.lhamster.service.NavService;
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
public class NavServiceImpl implements NavService {
    @Autowired
    private BlogNavMapper blogNavMapper;

    @Override
    @Cacheable(cacheNames = "nav", key = "#root.targetClass")
    public List<BlogNav> navs() {
        return blogNavMapper.selectAll();
    }

    @Override
    public BlogNav nav(Integer id) {
        return blogNavMapper.selectByPrimaryKey(id);
    }

    @Override
    @CacheEvict(cacheNames = "nav", key = "#root.targetClass")
    public void addNav(String name, String navUrl) {
        BlogNav blogNav = new BlogNav(null, name, navUrl);
        blogNavMapper.insert(blogNav);
    }

    @Override
    @CacheEvict(cacheNames = "nav", key = "#root.targetClass")
    public void updateNav(Integer id, String name, String navUrl) {
        BlogNav nav = blogNavMapper.selectByPrimaryKey(id);
        nav.setNavContent(name);
        nav.setNavUrl(navUrl);
        // 插入数据库
        blogNavMapper.updateByPrimaryKey(nav);
    }

    @Override
    @CacheEvict(cacheNames = "nav", key = "#root.targetClass")
    public void deleteNav(Integer id) {
        blogNavMapper.deleteByPrimaryKey(id);
    }
}
