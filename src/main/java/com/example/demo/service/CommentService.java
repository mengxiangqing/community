package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.CommentDTO;
import com.example.demo.enums.CommentTypeEnum;
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

    // 根据ID找评论
	public List<CommentDTO> listByTargetId(Integer id,Integer type) {
        List<Comment> comments = commentMapper.listByTargetId(id,type);
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
     //找到这个id的评论
     public CommentDTO getById(Integer id) {
        Comment comment= commentMapper.getById(id);// 通过id找到
        CommentDTO commentDTO = new CommentDTO();// 创建传输对象
        BeanUtils.copyProperties(comment, commentDTO);// 复制过去
        User user = userMapper.findById(comment.getCommenter());// 通过评论者找到用户表中的用户
        commentDTO.setUser(user);
        return commentDTO;
    }


}