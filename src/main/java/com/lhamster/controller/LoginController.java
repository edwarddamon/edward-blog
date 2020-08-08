package com.lhamster.controller;

import com.alibaba.fastjson.JSONObject;
import com.lhamster.domain.Audience;
import com.lhamster.domain.BlogUser;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import com.lhamster.service.UserService;
import com.lhamster.util.JwtTokenUtil;
import com.lhamster.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Damon_Edward
 * @version 1.0
 * @date 2020/7/20
 */
@Slf4j
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private Audience audience;

    /**
     * QQ登录
     *
     * @param openId
     * @param accessToken
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/qqLogin")
    public Result qqLogin(String openId, String accessToken, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(accessToken)) {
            throw new ResultException(ResultCode.EMPTY);
        }
        // 获取用户信息
        String url = "https://graph.qq.com/user/get_user_info?access_token=" + accessToken + "&oauth_consumer_key=" + LoginUtil.QQ_appId + "&openid=" + openId;
        JSONObject userInfo = LoginUtil.getUserInfo(url);
        if (StringUtils.isEmpty(userInfo)) {
            throw new ResultException(ResultCode.USER_ACCESS_QQ_FAILED);
        }
        try {
            // 三方登录,如果数据库不存在将数据插入数据
            BlogUser user = userService.loginThird(openId, userInfo.getString("nickname"), userInfo.getString("figureurl_qq_1"), userInfo.getString("gender"));
            // 生成token
            String token = JwtTokenUtil.createJWT(user.getUId().toString(), user.getUNickname(), "user", audience);
            Map<String, String> map = new HashMap<>();
            map.put(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);
            // 将token存入响应体中返回给前端
            return new Result<>(ResultCode.USER_LOGIN_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.USER_LOGIN_FAILED);
        }
    }
}
