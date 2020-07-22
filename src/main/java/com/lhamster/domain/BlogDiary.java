package com.lhamster.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDiary {
    private Integer dId;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dDiarytime;
    private String dPicture;
    private String dContent;
}