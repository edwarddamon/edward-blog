package com.lhamster.domain.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result<T> {
    private Integer code;
    private String msg;

    private Integer count; // 返回的总数据数目

    private T data; // 存放返回的数据

    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
    }

    public Result(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
        this.data = data;
    }

    public Result(ResultCode resultCode, T data, Integer count) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
        this.data = data;
        this.count = count;
    }
}
