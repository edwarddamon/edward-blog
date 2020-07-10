package com.lhamster.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogNav {
    private Integer navId;
    private String navContent;
    private String navUrl;
}