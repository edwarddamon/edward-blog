package com.lhamster.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryVo {
    private Integer pageNum; // 当前页码
    private Integer pageSize; // 每一页的个数

    private String email; // 邮箱号搜索
    private String nickName; // 用户昵称搜索
}
