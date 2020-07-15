package com.lhamster.service;

import com.lhamster.domain.BlogUser;
import com.lhamster.domain.request.QueryVo;
import com.lhamster.domain.response.Result;

import java.util.List;

public interface UserService {
    Boolean checkPhone(String phone);/*检查手机号是否注册过*/

    void register(BlogUser blogUser);/*注册用户*/

    BlogUser login(String username, String password, String type);/*登录*/

    BlogUser updateUser(BlogUser blogUser);/*完善/修改用户信息*/

    Boolean checkOldPwd(String oldPwd, String userId);/*检查旧密码是否正确*/

    void updatePwd(String newPwd, String userId);/*修改密码*/

    void setNewPwd(String phone, String password);/*手机短信重置密码*/

    BlogUser queryById(int parseInt);/*根据id查询用户*/

    void updateHeadPic(Integer uId, String headPicUrl);/*修改用户头像地址*/

    Result<List<BlogUser>> queryAll(QueryVo vo);/*查询所有*/

    void setAdmin(Integer id, Integer decide);/*设置取消管理员*/
}
