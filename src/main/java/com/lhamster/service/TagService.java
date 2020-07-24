package com.lhamster.service;

import com.lhamster.domain.BlogTag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/24
 */
public interface TagService {
    /*标签列表*/
    List<BlogTag> tags();

    /*查询一个标签*/
    BlogTag tag(Integer id);

    /*添加标签*/
    void addTag(String content);

    /*更新标签*/
    void updateTag(Integer id, String content);

    /*删除标签*/
    void deleteTag(Integer id);
}
