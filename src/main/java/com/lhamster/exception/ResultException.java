package com.lhamster.exception;

import com.lhamster.domain.response.ResultCode;

/**
 * 自定义异常，处理返回出问题的
 *
 * @author edward
 * @since 2020/6/26
 */
public class ResultException extends RuntimeException {

    private ResultCode resultCode;

    public ResultException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * 返回错误信息
     */
    public ResultCode getResultMap() {
        return this.resultCode;
    }
}
