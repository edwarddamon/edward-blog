package com.lhamster.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhamster.domain.BlogMessage;
import com.lhamster.domain.BlogUser;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import com.lhamster.mapper.BlogMessageMapper;
import com.lhamster.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/21
 */
@Service
@Transactional
@Slf4j
public class MessageServiceImpl implements MessageService {
    @Autowired
    private BlogMessageMapper blogMessageMapper;

    @Override
    public Result<List<BlogMessage>> getMessage(QueryVo vo, Boolean top, Boolean like, String content) {
        Page<Object> page = PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        return new Result<>(ResultCode.SUCCESS, blogMessageMapper.selectAll(top, like, content), (int) page.getTotal());
    }

    @Override
    public void setLike(Integer id, Boolean like) {
        BlogMessage message = blogMessageMapper.selectByPrimaryKey(id);
        if (like) {
            message.setMesLikecount(message.getMesLikecount() + 1);
        } else {
            message.setMesLikecount(message.getMesLikecount() - 1);
        }
        blogMessageMapper.updateByPrimaryKey(message);
    }

    @Override
    public List<BlogMessage> getMyMessage(int parseInt) {
        return blogMessageMapper.selectByUserId(parseInt);
    }

    @Override
    public void addMessage(String content, Integer target, Integer parent, String userId) {
        BlogMessage message = new BlogMessage();
        message.setMesContent(content);
        message.setMesLikecount(0);
        message.setMesMestime(new Date());
        message.setMesTop(false);
        BlogUser user = new BlogUser();
        user.setUId(Integer.parseInt(userId));
        message.setCurrUser(user);
        if (!StringUtils.isEmpty(target)) {
            BlogUser blogUser = new BlogUser();
            blogUser.setUId(target);
            message.setTargetUser(blogUser);
        }
        message.setMesParentId(parent);
        log.info(message.toString());
        // 插入数据库
        blogMessageMapper.insert(message);
    }

    @Override
    public void setTop(Integer id, Boolean top) {
        blogMessageMapper.setTop(id, top);
    }

    @Override
    public void deleteMsg(Integer id, String type, String userId) {
        if ("front".equals(type)) {
            BlogMessage message = blogMessageMapper.selectByPrimaryKey(id);
            if (Integer.parseInt(userId) != message.getCurrUser().getUId()) {
                throw new ResultException(ResultCode.MESSAGE_DELETE_SUCCESS);
            }
        }
        /*删除子留言*/
        List<BlogMessage> childMessages = blogMessageMapper.selectByParentId(id);
        if (!StringUtils.isEmpty(childMessages)) {
            for (BlogMessage childMessage : childMessages) {
                blogMessageMapper.deleteByPrimaryKey(childMessage.getMesId());
            }
        }
        /*删除留言*/
        blogMessageMapper.deleteByPrimaryKey(id);
    }
}
