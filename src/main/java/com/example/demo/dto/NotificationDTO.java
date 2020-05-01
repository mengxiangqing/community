package com.example.demo.dto;

import com.example.demo.model.User;

import lombok.Data;

@Data
public class NotificationDTO {
    private Integer id;
    private Long gmtCreate;
    private Integer status;
    private User notifier;
    private String notifierName;
    private String outerTitle;
    private Integer outerId;
    private String typeName;
    private Integer type;
}