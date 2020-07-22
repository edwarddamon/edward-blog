package com.lhamster.service;

import com.lhamster.domain.BlogBook;
import com.lhamster.domain.response.Result;

import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/22
 */
public interface BookService {
    /*书籍列表*/
    Result<List<BlogBook>> book();

    /*添加书籍*/
    void addBook(String name, String des, String picUrl);

    /*查询一本书*/
    BlogBook queryBook(Integer id);

    /*修改书籍*/
    BlogBook updateBook(Integer id, String name, String des, String newPicUrl);

    /*删除书籍*/
    void deleteBook(Integer id);
}
