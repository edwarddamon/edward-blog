package com.lhamster.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogFriendblog {
    private Integer fId;
    private String fName;
    private String fUrl;
    private Integer fStatus;
    private String fBackinfo;
    private Date fFriendblogtime;
    /*用户*/
    private BlogUser user;
}