package com.example.demo.service;

import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Comment;
import com.example.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

	public void insert(Comment comment) {
        commentMapper.insert(comment);
	}

}