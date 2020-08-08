package com.lhamster.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogComment {
    private Integer comId;
    private String comContent;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date comComtime;
    private Integer comLikecount;
    private Integer comComId;
    private Integer comArticleId;

    // 子评论
    private List<BlogComment> blogComments;

    // 目标用户
    private BlogUser targetUser;

    // 用户
    private BlogUser blogUser;
}