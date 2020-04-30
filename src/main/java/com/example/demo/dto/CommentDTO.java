package com.example.demo.dto;

import com.example.demo.model.User;

import lombok.Data;

@Data
public class CommentDTO {	
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commenter;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private User user;
}	