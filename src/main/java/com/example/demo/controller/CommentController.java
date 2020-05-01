package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.dto.CommentCreateDTO;
import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.ResultDTO;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.mapper.NotificationMapper;
import com.example.demo.model.Comment;
import com.example.demo.model.Notification;
import com.example.demo.model.User;
import com.example.demo.service.CommentService;
import com.example.demo.service.TextService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private TextService textService;
    @Autowired
    private NotificationMapper notificationMapper;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentID(commentCreateDTO.getParentId());//父类评论的ID
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(0);
        comment.setCommenter(user.getId());//评论人
        textService.incComment(commentCreateDTO.getParentId(), commentCreateDTO.getType());//增加被回复的内容评论数
        comment.setCommentCount(0);
        //创建通知
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setNotifier(comment.getCommenter());// 评论者ID
        notification.setOuterId(comment.getParentID());// 被评论者的内容的ID
        notification.setType(comment.getType());
        notification.setStatus(0);// 设置未读状态
        //通过被评论的内容的id找到接收者
        if(notification.getType()==1)//评论文章的通知
        {
            User user2= textService.getById(comment.getParentID()).getUser();
            notification.setReceiver(user2.getId());
            
        }else if(notification.getType()==2)//回复评论的通知
        {
            User user3=commentService.getById(comment.getParentID()).getUser();
            notification.setReceiver(user3.getId());
        }
        commentService.insert(comment);//插入评论
        notificationMapper.insert(notification);
        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Integer id, Model model) {
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, 2);
        model.addAttribute("commentDTOS", commentDTOS);
        return ResultDTO.okOf();
    }
}