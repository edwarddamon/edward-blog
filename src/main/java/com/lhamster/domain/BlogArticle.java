package com.lhamster.domain;

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
    private Date aCreatetime;
    private Integer aLikecount;
    private Integer aVisitcount;
    private Boolean aStatus;
    private String aPicture;
    private Integer aCateId;
    private Integer aUserId;
    private String aContent;
}