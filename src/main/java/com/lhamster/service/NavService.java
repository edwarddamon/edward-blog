package com.lhamster.service;

import com.lhamster.domain.BlogNav;

import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/24
 */
public interface NavService {
    /*导航栏数据列表*/
    List<BlogNav> navs();

    /*查询某个导航栏数据*/
    BlogNav nav(Integer id);

    /*添加导航栏数据*/
    void addNav(String name, String navUrl);

    /*跟新导航栏数据*/
    void updateNav(Integer id, String name, String navUrl);

    /*删除导航栏数据*/
    void deleteNav(Integer id);
}
