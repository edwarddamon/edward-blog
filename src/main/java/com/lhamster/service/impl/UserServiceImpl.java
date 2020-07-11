package com.lhamster.service.impl;

import com.lhamster.mapper.BlogUserMapper;
import com.lhamster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private BlogUserMapper blogUserMapper;

    @Override
    public Boolean checkPhone(String phone) {
        if (!StringUtils.isEmpty(blogUserMapper.selectByPhone(phone))) { // 存在
            return true;
        } else { // 不存在
            return false;
        }
    }
}
