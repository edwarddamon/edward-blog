package com.lhamster.domain;

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
    private String bPicture;
    private String bDiscrible;
    private Date bBooktime;
}