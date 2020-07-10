package com.lhamster.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogUser {
    private Integer uId;
    private String uEmail;
    private String uPassword;
    private String uNickname;
    private String uHeadpicture;
    private Boolean uSex;
    private String uPhone;
    private Date uBirthday;
    private Boolean uAdmin;
}