package com.lhamster.domain.response;

/**
 * 存放响应信息
 */
public enum ResultCode {
    /* 成功状态码 */
    SUCCESS(0, "操作成功！"),

    /* 错误状态码 */
    FAIL(-1, "操作失败！"),

    /*用户登录：2001-2999*/
    USER_LOGIN_SUCCESS(2000, "登陆成功"),
    USER_NOT_LOGGED_IN(2001, "用户未登录，请先登录"),
    USER_LOGIN_ERROR(2002, "用户名或密码错误"),

    /*系统错误：3000-3999*/
    SYSTEM_UNKNOWN_TOKEN(3000, "未知错误"),

    /*token权限错误：4000-4999*/
    PERMISSION_TOEKN_CREATE_FAILE(4000, "签名失败"),
    PERMISSION_TOKEN_EXPIRED(4001, "token已过期"),
    PERMISSION_TOKEN_INVALID(4002, "无效token");

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
