package com.lhamster.controller;

import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import com.lhamster.service.UserService;
import com.lhamster.util.SmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserService userService;

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
}
