package com.lhamster.service;

import com.lhamster.domain.BlogDiary;

import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/22
 */
public interface DiaryService {
    List<BlogDiary> diaries();/*日记列表*/

    /*添加日记*/
    void addDiiary(String content, String picUrl);

    /*修改日记*/
    BlogDiary updateDiary(Integer id, String content, String newPicUrl);

    /*删除日记*/
    void deleteDiary(Integer id);
}
