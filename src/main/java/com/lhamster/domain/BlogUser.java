package com.lhamster.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date uBirthday;
    private Boolean uAdmin;
}