package com.lhamster.service;

import com.lhamster.domain.BlogMessage;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;

import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/21
 */
public interface MessageService {
    /*前台查询留言*/
    Result<List<BlogMessage>> getMessage(QueryVo vo, Boolean top, Boolean like, String content);

    /*点赞*/
    void setLike(Integer id, Boolean like);

    /*获取我发布的留言*/
    List<BlogMessage> getMyMessage(int parseInt);

    /*发布留言*/
    void addMessage(String content, Integer target, Integer parent, String userId);

    /*留言置顶*/
    void setTop(Integer id, Boolean top);

    /*删除留言*/
    void deleteMsg(Integer id, String type, String userId);
}
