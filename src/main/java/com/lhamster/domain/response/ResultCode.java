package com.lhamster.domain.response;

/**
 * 存放响应信息
 */
public enum ResultCode {
    /* 成功状态码 */
    SUCCESS(0, "操作成功！"),

    /* 错误状态码 */
    FAIL(-1, "操作失败！"),
    EMPTY(-10, "参数不能为空！"),
    VISIT_COUNT_FAILED(-20, "博客访问次数统计失败！"),

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
    USER_HEADPIC_FAILED(2020, "上传头像失败"),
    USER_HEADPIC_SUCCESS(2030, "更换头像成功"),
    USER_HEADPIC_FAIL(2040, "更换头像失败"),
    USER_HEADPIC_TYPE_ERROR(2050, "上传的文件格式不符合要求"),
    USER_HEADPIC_EMPTY(2060, "头像地址不能为空"),
    USER_ADD_EMPTY(2070, "手机号或密码不能为空"),
    USER_DEL_FAILED(2080, "删除用户失败"),
    USER_DEL_SUCCESS(2090, "删除用户成功"),
    USER_LOGIN_FAILED(2100, "登录失败"),
    USER_ACCESS_QQ_FAILED(2200, "获取QQ用户信息失败"),

    /*系统错误：3000-3999*/
    SYSTEM_UNKNOWN_TOKEN(3000, "未知错误"),
    SYSTEM_UNKNOWN_CHECKED(3001, "检验失败"),

    /*token权限错误：4000-4999*/
    PERMISSION_TOEKN_CREATE_FAILE(4000, "签名失败"),
    PERMISSION_TOKEN_EXPIRED(4001, "登录失效,请重新登录"),
    PERMISSION_TOKEN_INVALID(4002, "无效token"),
    PERMISSION_ORIGINAL_ERROR(4003, "请求来源异常"),

    /*文章错误：5000-5999*/
    ARTICLE_UPLOAD_SUCCESS(5000, "图片上传成功"),
    ARTICLE_UPLOAD_FAILED(5010, "图片上传失败"),
    ARTICLE_DELETE_FAILED(5012, "图片删除失败"),
    ARTICLE_DELETE_SUCCESS(5014, "图片删除成功"),
    CATEGORY_ADD_FAILED(5020, "添加分类失败"),
    CATEGORY_ADD_SUCCESS(5030, "添加分类成功"),
    CATEGORY_ADD_EXISTED(5040, "该分类已存在"),
    CATEGORY_UPDATE_FAILED(5050, "修改分类失败"),
    CATEGORY_UPDATE_SUCCESS(5060, "修改分类成功"),
    CATEGORY_DELETE_FAILED(5070, "删除分类失败"),
    CATEGORY_DELETE_SUCCESS(5080, "删除分类成功"),
    ARTICLE_UPDATE_FAILED(5082, "文章修改失败"),
    ARTICLE_UPDATE_SUCCESS(5084, "文章修改成功"),
    BLOGARTICLE_DELETE_FAILED(5086, "文章删除失败"),
    BLOGARTICLE_DELETE_SUCCESS(5088, "文章删除成功"),
    ARTICLE_RECOVER_FAILED(5089, "文章恢复失败"),
    ARTICLE_RECOVER_SUCCESS(5189, "文章恢复成功"),
    ARTICLE_PUBLISH_FAILED(5090, "发布文章失败"),
    ARTICLE_PUBLISH_SUCCESS(5100, "发布文章成功"),
    COMMENT_PUBLISH_FAILED(5105, "评论发布失败"),
    COMMENT_PUBLISH_EMPTY(5106, "评论内容不能为空"),
    COMMENT_PUBLISH_SUCCESS(5110, "评论发布成功"),
    COMMENT_DELETE_FAILED(5115, "评论删除失败"),
    COMMENT_DELETE_SUCCESS(5120, "评论删除成功"),

    /*消息：6000-6100*/
    INFORM_READ_SUCCESS(6000, "消息已读设置成功"),
    INFORM_READ_FAILED(6001, "消息已读设置失败"),
    INFORM_DELETE_SUCCESS(6005, "消息删除成功"),
    INFORM_DELETE_FAILED(6008, "消息删除失败");

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
