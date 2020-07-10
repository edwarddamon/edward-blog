package com.lhamster.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogComment {
    private Integer comId;
    private String comContent;
    private Date comComtime;
    private Integer comLikecount;
    private Integer comComId;
    private Integer comArticleId;
    private Integer comMessageId;
    private Integer comUserId;
}