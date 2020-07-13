package com.lhamster.service;

import com.lhamster.domain.BlogUser;

public interface UserService {
    Boolean checkPhone(String phone);/*检查手机号是否注册过*/

    void register(BlogUser blogUser);/*注册用户*/

    BlogUser login(String username, String password, String type);/*登录*/
}
