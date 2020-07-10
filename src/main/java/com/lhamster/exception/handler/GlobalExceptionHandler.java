package com.lhamster.exception.handler;

import com.lhamster.domain.response.Result;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author edward
 * @since 2020/6/26
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的异常
     */
    @ExceptionHandler(ResultException.class)
    public Result handleException(ResultException e) {
        // 打印异常信息
        log.error("===异常信息===" + e.getMessage());
        return new Result(e.getResultMap());
    }

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Exception.class)
    public Result handleOtherException(Exception e) {
        //打印异常堆栈信息
        e.printStackTrace();
        // 打印异常信息
        log.error("===不可知异常===" + e.getMessage());
        return new Result(ResultCode.SYSTEM_UNKNOWN_TOKEN);
    }
}
