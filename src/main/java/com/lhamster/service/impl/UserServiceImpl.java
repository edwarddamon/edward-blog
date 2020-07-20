package com.lhamster.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhamster.domain.BlogUser;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import com.lhamster.mapper.BlogUserMapper;
import com.lhamster.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Random;

@Slf4j
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
    public BlogUser updateUser(BlogUser blogUser) {
        blogUserMapper.updateByPrimaryKey(blogUser);
        return blogUser;
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

    @Override
    public Result<List<BlogUser>> queryAll(QueryVo vo) {
        Page<Object> page = PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<BlogUser> blogUsers = blogUserMapper.selectAll(vo);
        return new Result<List<BlogUser>>(ResultCode.SUCCESS, blogUsers, (int) page.getTotal());
    }

    @Override
    public void setAdmin(Integer id, Integer decide) {
        blogUserMapper.setAdmin(id, decide);
    }

    @Override
    public BlogUser loginThird(String identityId, String nickName, String headPicUrl, String sex) {
        if (StringUtils.isEmpty(identityId) || StringUtils.isEmpty(nickName) || StringUtils.isEmpty(headPicUrl) || StringUtils.isEmpty(sex)) {
            throw new ResultException(ResultCode.USER_LOGIN_FAILED);
        }
        BlogUser blogUser = blogUserMapper.selectByIdentify(identityId);
        if (!StringUtils.isEmpty(blogUser)) {
            return blogUser;
        }
        blogUser = new BlogUser();
        blogUser.setUNickname(nickName);
        blogUser.setUPassword(identityId);
        blogUser.setUHeadpicture(headPicUrl);
        blogUser.setUSex("男".equals(sex));
        blogUser.setUAdmin(false);
        // 插入数据库
        blogUserMapper.insert(blogUser);
        log.info(blogUser.toString());
        return blogUser;
    }
}
