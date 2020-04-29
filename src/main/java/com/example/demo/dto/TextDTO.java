package com.example.demo.dto;

import com.example.demo.model.User;

import lombok.Data;

@Data
public class TextDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Integer creator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private User user;
}