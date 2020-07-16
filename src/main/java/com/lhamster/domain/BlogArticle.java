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
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date aCreatetime;
    private Integer aLikecount;
    private Integer aVisitcount;
    private Boolean aStatus;
    private String aPicture;
    private Integer aCateId;
    private Integer aUserId;
    private String aContent;
}