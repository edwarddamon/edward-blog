package com.lhamster.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogBook {
    private Integer bId;
    private String bName;
    private String bDiscrible;
    private String bPicture;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date bBooktime;
}