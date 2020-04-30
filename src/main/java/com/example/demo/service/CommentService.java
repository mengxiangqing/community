package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.CommentDTO;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Comment;
import com.example.demo.model.User;

import org.springframework.beans.BeanUtils;
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

	public List<CommentDTO> listByTextId(Integer id) {
        List<Comment> comments = commentMapper.listByTextId(id);
        List<CommentDTO> commentDTOs=new ArrayList<>();
        for(Comment comment:comments)
        {
            User user=userMapper.findById(comment.getCommenter());
            CommentDTO commentDTO=new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(user);
            commentDTOs.add(commentDTO);
        }
		return commentDTOs;
	}

}