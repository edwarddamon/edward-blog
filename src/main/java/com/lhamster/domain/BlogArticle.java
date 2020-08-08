package com.lhamster.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogArticle {
    private Integer aId;
    private String aTitle;
    private String aDesc;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date aCreatetime;
    private Integer aLikecount;
    private Integer aVisitcount;
    private Boolean aStatus;
    private String aPicture;
    private Integer aCateId;
    private Integer aUserId;
    private String aContent;

    /*级联文章类型*/
    private BlogCategory cate;

    /*级联文章作者*/
    private BlogUser user;
}