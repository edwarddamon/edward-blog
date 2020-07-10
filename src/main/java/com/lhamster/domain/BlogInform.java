package com.lhamster.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogInform {
    private Integer inId;
    private String inContent;
    private Date inInformtime;
    private Integer inUserId;
}