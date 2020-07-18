package com.lhamster.service;

import com.lhamster.domain.BlogInform;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;

import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/18
 */
public interface InformService {
    Result<List<BlogInform>> selectAll(QueryVo vo, Boolean read, String userId);/*消息列表*/

    void setRead(Integer informId);/*设置消息已读*/

    void setAllRead(String userId);/*设置全部已读*/

    void deleteInform(Integer informId);/*删除消息通知*/
}
