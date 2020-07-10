package com.lhamster.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDiary {
    private Integer dId;
    private Date dDiarytime;
    private String dPicture;
    private String dContent;
}