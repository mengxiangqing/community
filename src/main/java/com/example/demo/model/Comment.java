package com.example.demo.model;

import lombok.Data;

@Data
public class Comment {
    private Integer id;

    private Integer parentID;

    private Integer type;

    private Integer commenter;

    private Long gmtCreate;

    private Long gmtModified;

    private Integer likeCount;

    private Integer commentCount;

    private String content;

    
}