package com.lhamster.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date fFriendblogtime;
    /*用户*/
    private BlogUser user;
}