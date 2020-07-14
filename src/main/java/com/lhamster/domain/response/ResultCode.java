package com.lhamster.domain.response;

/**
 * 存放响应信息
 */
public enum ResultCode {
    /* 成功状态码 */
    SUCCESS(0, "操作成功！"),

    /* 错误状态码 */
    FAIL(-1, "操作失败！"),

    /*短信错误：1000-1999*/
    SMS_SEND_FAILED(1000, "短信发送失败"),
    SMS_SEND_UNKNOWERROR(1001, "短信发送失败，未知错误"),
    SMS_SEND_SUCCESS(1002, "短信发送成功"),
    SMS_GET_FAILED(1003, "验证码读取失败"),
    SMS_GET_PASSED(1004, "验证码已超时"),
    SMS_GET_WRONG(1005, "验证码错误"),

    /*注册/用户登录：2001-2999*/
    USER_LOGIN_SUCCESS(2000, "登陆成功"),
    USER_NOT_LOGGED_IN(2001, "用户未登录，请先登录"),
    USER_LOGIN_ERROR(2002, "用户名或密码错误"),
    USER_REGISTER_EXISTED(2003, "该手机号已存在"),
    USER_REGISTER_SUCCESS(2004, "注册成功"),
    USER_UPDATE_SUCCESS(2005, "完善/修改成功"),
    USER_UPDATE_FAILED(2006, "完善/修改失败"),
    USER_PWD_WRONG(2007, "旧密码错误"),
    USER_PWD_FAILED(2008, "密码修改失败"),
    USER_PWD_SUCCESS(2009, "密码修改成功"),
    USER_PWD_RESET_SUCCESS(2010, "密码重置成功"),

    /*系统错误：3000-3999*/
    SYSTEM_UNKNOWN_TOKEN(3000, "未知错误"),
    SYSTEM_UNKNOWN_CHECKED(3001, "检验失败"),

    /*token权限错误：4000-4999*/
    PERMISSION_TOEKN_CREATE_FAILE(4000, "签名失败"),
    PERMISSION_TOKEN_EXPIRED(4001, "token已过期"),
    PERMISSION_TOKEN_INVALID(4002, "无效token"),
    PERMISSION_ORIGINAL_ERROR(4003, "请求来源异常");

    //操作代码
    private Integer code;
    //提示信息
    private String message;

    // 构造方法
    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
