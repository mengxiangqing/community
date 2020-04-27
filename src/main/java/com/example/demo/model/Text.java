package com.example.demo.model;

import lombok.Data;

@Data
public class Text {
    private Integer id;
    private String title;
    private String description;
    private String column;
    private Integer creator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;

    
}