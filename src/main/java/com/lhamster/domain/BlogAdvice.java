package com.lhamster.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogAdvice {
    private Integer adId;
    private String adTitle;
    private Date adTime;
    private Integer adUserId;
    private String adContent;
}