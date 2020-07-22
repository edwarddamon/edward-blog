package com.lhamster.service.impl;

import com.lhamster.domain.BlogDiary;
import com.lhamster.mapper.BlogDiaryMapper;
import com.lhamster.service.DiaryService;
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
public class DiaryServiceImpl implements DiaryService {
    @Autowired
    private BlogDiaryMapper blogDiaryMapper;

    @Override
    @Cacheable(cacheNames = "diary", key = "#root.targetClass")
    public List<BlogDiary> diaries() {
        return blogDiaryMapper.selectAll();
    }

    @Override
    @CacheEvict(cacheNames = "diary", key = "#root.targetClass")
    public void addDiiary(String content, String picUrl) {
        BlogDiary blogDiary = new BlogDiary(null, new Date(), picUrl, content);
        blogDiaryMapper.insert(blogDiary);
    }

    @Override
    @CachePut(cacheNames = "diary", key = "#id")
    @CacheEvict(cacheNames = "diary", key = "#root.targetClass")
    public BlogDiary updateDiary(Integer id, String content, String newPicUrl) {
        BlogDiary diary = blogDiaryMapper.selectByPrimaryKey(id);
        if (!StringUtils.isEmpty(content)) {
            diary.setDContent(content);
        }
        if (!StringUtils.isEmpty(newPicUrl)) {
            // 删除存储桶的旧图片
            String dPicture = diary.getDPicture();
            if (!StringUtils.isEmpty(dPicture)) {
                TencentCOSUtil.deletefile("diaryPicture" + dPicture.substring(dPicture.lastIndexOf("/")));
            }
            diary.setDPicture(newPicUrl);
        }
        // 插入数据库
        blogDiaryMapper.updateByPrimaryKey(diary);
        return diary;
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "diary", key = "#id"),
                    @CacheEvict(cacheNames = "diary", key = "#root.targetClass")
            }
    )
    public void deleteDiary(Integer id) {
        BlogDiary diary = blogDiaryMapper.selectByPrimaryKey(id);
        if (!StringUtils.isEmpty(diary.getDPicture())) {
            // 删除存储桶中的相关图片
            TencentCOSUtil.deletefile("diaryPicture" + diary.getDPicture().substring(diary.getDPicture().lastIndexOf("/")));
        }
        blogDiaryMapper.deleteByPrimaryKey(id);
    }
}
