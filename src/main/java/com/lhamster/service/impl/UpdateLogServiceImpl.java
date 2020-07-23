package com.lhamster.service.impl;

import com.lhamster.domain.BlogUpdatelog;
import com.lhamster.mapper.BlogUpdatelogMapper;
import com.lhamster.service.UpdateLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/23
 */
@Slf4j
@Service
@Transactional
public class UpdateLogServiceImpl implements UpdateLogService {
    @Autowired
    private BlogUpdatelogMapper blogUpdatelogMapper;

    @Override
    @Cacheable(value = "updateLog", key = "#root.targetClass")
    public List<BlogUpdatelog> updateLogList() {
        return blogUpdatelogMapper.selectAll();
    }

    @Override
    @CacheEvict(value = "updateLog", key = "#root.targetClass")
    public void addUpdateLog(String content) {
        blogUpdatelogMapper.insert(new BlogUpdatelog(null, new Date(), content));
    }

    @Override
    @CacheEvict(value = "updateLog", key = "#root.targetClass")
    public BlogUpdatelog updateUpdateLog(Integer id, String content) {
        BlogUpdatelog updatelog = blogUpdatelogMapper.selectByPrimaryKey(id);
        updatelog.setUpContent(content);
        // 更新
        blogUpdatelogMapper.updateByPrimaryKey(updatelog);
        return updatelog;
    }

    @Override
    @CacheEvict(value = "updateLog", key = "#root.targetClass")
    public void deleteLog(Integer id) {
        blogUpdatelogMapper.deleteByPrimaryKey(id);
    }
}
