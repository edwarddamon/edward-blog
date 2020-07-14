package com.lhamster.service.impl;

import com.lhamster.domain.BlogUser;
import com.lhamster.mapper.BlogUserMapper;
import com.lhamster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Random;

@Service
@Transactional
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

    @Override
    public void register(BlogUser blogUser) {
        Random random = new Random();
        // 初始化头像
        String headPic = "https://lhamster-edward-blog-1302533254.cos.ap-nanjing.myqcloud.com/headPicture/" + random.nextInt(5) + ".jpg";
        blogUser.setUHeadpicture(headPic);
        // 初始化nickname
        blogUser.setUNickname(blogUser.getUPhone());
        blogUserMapper.insert(blogUser);
    }

    @Override
    public BlogUser login(String userPhone, String password, String type) {
        return blogUserMapper.login(userPhone, password, type);
    }

    @Override
    public void updateUser(BlogUser blogUser) {
        blogUserMapper.updateByPrimaryKey(blogUser);
    }

    @Override
    public Boolean checkOldPwd(String oldPwd, String userId) {
        BlogUser user = blogUserMapper.selectByPrimaryKey(Integer.valueOf(userId));
        return oldPwd.equals(user.getUPassword());
    }

    @Override
    public void updatePwd(String newPwd, String userId) {
        blogUserMapper.updatePwd(Integer.parseInt(userId), newPwd);
    }

    @Override
    public void setNewPwd(String phone, String password) {
        blogUserMapper.resetPwd(phone, password);
    }

    @Override
    public BlogUser queryById(int parseInt) {
        return blogUserMapper.selectByPrimaryKey(parseInt);
    }

    @Override
    public void updateHeadPic(Integer uId, String headPicUrl) {
        blogUserMapper.updateHeadPic(uId, headPicUrl);
    }
}
