package com.lhamster.service;

import com.lhamster.domain.BlogUpdatelog;

import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/23
 */
public interface UpdateLogService {
    List<BlogUpdatelog> updateLogList();/*更新日志列表*/

    void addUpdateLog(String content);/*添加更新日志*/

    BlogUpdatelog updateUpdateLog(Integer id, String content);/*修改更新日志*/

    void deleteLog(Integer id);/*删除日志*/
}
