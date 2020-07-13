package com.lhamster.controller;

import com.lhamster.domain.BlogUser;
import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import com.lhamster.service.UserService;
import com.lhamster.util.SmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /*注入redisTemplate*/
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 短信验证
     *
     * @param "手机号"
     * @return Result
     */
    @PostMapping("/user/{phone}")
    public Result messageCheck(@PathVariable("phone") String phone) {
        log.info("手机号======" + phone);
        try {
            // 检查手机号是否已经存在
            Boolean result = userService.checkPhone(phone);
            if (result) {// 该手机号已存在
                return new Result(ResultCode.USER_REGISTER_EXISTED);
            } else {// 该手机号不存在,发送短信验证
                String randomCode = SmsUtils.generateRandomCode();// 六位随机数验证码
                long currTime = new Date().getTime();// 当前时间的时间戳
                String res = SmsUtils.sendSms(randomCode, new String[]{"86" + phone}, 0);
                if ("success".equals(res)) {// 发送成功
                    // 以手机号为key，将随机数验证码和当前时间的时间戳存入redis中
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("code", randomCode);
                    map.put("time", currTime);
                    redisTemplate.opsForHash().putAll(phone, map);
                    log.info(phone + "   " + randomCode + "   " + currTime);
                    return new Result(ResultCode.SMS_SEND_SUCCESS);
                } else {// 发送失败
                    return new Result(ResultCode.SMS_SEND_FAILED);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultCode.SYSTEM_UNKNOWN_CHECKED);
        }
    }

    /**
     * 注册
     *
     * @param blogUser
     * @return
     */
    @PostMapping("/register")
    public Result register(BlogUser blogUser, String code) {
        log.info("requestInfo====" + blogUser.toString());
        log.info("code===" + code);
        // 去redis缓存检查code是否存在，对比是否过期
        String redisCode = (String) redisTemplate.opsForHash().get(blogUser.getUPhone(), "code");
        long redisTime = (long) redisTemplate.opsForHash().get(blogUser.getUPhone(), "time");
        if (!StringUtils.isEmpty(redisCode) && !StringUtils.isEmpty(redisTime)) { // 读取到了存入redis的该手机号的信息
            // 判断该信息是否过期
            long nowTime = new Date().getTime();
            if (nowTime - redisTime < 120 * 1000) { // 没过期：对比redisCode和前端传递的code是否一致
                if (redisCode.equals(code)) {// 一致
                    //将手机号和密码存入数据库，注册成功
                    try {
                        userService.register(blogUser);
                        // 删除redis中的相关信息
                        redisTemplate.delete(blogUser.getUPhone());
                        return new Result(ResultCode.USER_REGISTER_SUCCESS);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new ResultException(ResultCode.FAIL);
                    }
                } else { // 不一致
                    return new Result(ResultCode.SMS_GET_WRONG);
                }
            } else { // 过期
                redisTemplate.delete(blogUser.getUPhone());
                throw new ResultException(ResultCode.SMS_GET_PASSED);
            }
        } else {
            throw new ResultException(ResultCode.SMS_GET_FAILED);
        }
    }
}
