package com.lhamster.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogBug {
    private Integer bugId;
    private Date bugTime;
    private Integer bugUserId;
    private String bugContent;
}