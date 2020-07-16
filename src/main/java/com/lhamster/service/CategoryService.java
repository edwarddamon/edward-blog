package com.lhamster.service;

import com.lhamster.domain.BlogCategory;
import com.lhamster.domain.response.Result;

import java.util.List;

public interface CategoryService {
    Result<List<BlogCategory>> queryAllCategory();/*查询所有*/

    void insertCategory(String name);/*添加分类*/

    BlogCategory selectOne(String name);/*检查分类是否已存在*/

    void updateCategory(BlogCategory blogCategory);/*修改分类*/

    void deleteCategory(Integer id);/*删除分类*/
}
