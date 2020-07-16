package com.lhamster.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryVo {
    private Integer pageNum; // 当前页码
    private Integer pageSize; // 每一页的个数

    private String email; // 邮箱号搜索
    private String nickName; // 用户昵称搜索

    /*博客文章列表参数*/
    private Integer userId; // 用户id
    private String title; // 标题名称
    private Integer cateId; // 分类id
    private Integer status; // 发布/删除 (参数默认为发布的，传递1为假删除的博客)
    private Integer judge; // 判断是否查询当前用户（默认查询所有，传递1位当前登录用户）
    private Integer sort; // 排序方式（默认时间排序，1按照访问量排序，2按照点赞排序）
}
