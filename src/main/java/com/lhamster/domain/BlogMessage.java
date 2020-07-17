package com.lhamster.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogMessage {
    private Integer mesId;
    private String mesContent;
    private Integer mesLikecount;
    private Date mesMestime;
    private Boolean mesTop;
    private Integer mesUserId;
    private Integer mesTargetId;
    private Integer mesParentId;
}