package com.lhamster.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhamster.domain.BlogInform;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.mapper.BlogInformMapper;
import com.lhamster.service.InformService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/18
 */
@Service
@Transactional
@Slf4j
public class InformServiceImpl implements InformService {
    @Autowired
    private BlogInformMapper blogInformMapper;

    @Override
    public Result<List<BlogInform>> selectAll(QueryVo vo, Integer read, Integer userId) {
        Boolean status = null;
        if (read.equals(1)) {
            status = false;
        }
        if (read.equals(2)) {
            status = true;
        }
        System.out.println(userId);
        Page<Object> page = PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        return new Result<>(ResultCode.SUCCESS, blogInformMapper.selectAll(status, userId), (int) page.getTotal());
    }

    @Override
    public void setRead(Integer informId) {
        blogInformMapper.updateByPrimaryKey(informId);
    }

    @Override
    public void setAllRead(String userId) {
        List<BlogInform> blogInforms = blogInformMapper.selectByUserId(Integer.parseInt(userId));
        for (BlogInform blogInform : blogInforms) {
            blogInformMapper.updateByPrimaryKey(blogInform.getInId());
        }
    }

    @Override
    public void deleteInform(Integer informId) {
        blogInformMapper.deleteByPrimaryKey(informId);
    }

    @Override
    public Integer selectCount(Integer userId) {
        return blogInformMapper.selectCount(userId);
    }
}
