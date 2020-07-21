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
public class BlogMessage {
    private Integer mesId;
    private String mesContent;
    private Integer mesLikecount;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date mesMestime;
    private Boolean mesTop;
    private Integer mesParentId;

    /*目标用户*/
    private BlogUser targetUser;

    /*当前用户*/
    private BlogUser currUser;

    /*子留言*/
    private List<BlogMessage> blogMessageList;
}