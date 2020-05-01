package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.dto.PaginationDTO;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.NotificationMapper;
import com.example.demo.mapper.TextMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Comment;
import com.example.demo.model.Notification;
import com.example.demo.model.NotificationExample;
import com.example.demo.model.Text;
import com.example.demo.model.User;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private TextMapper textMapper;
    
    @Autowired
    private CommentMapper commentMapper;
    

    public PaginationDTO list(Integer id, Integer page, Integer size) {

        //id是接收者的id
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id);
        Integer totalPage;
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);//找到个数
        if (totalCount == 0) {
            return null;
        }
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (totalCount == 0) {
            totalPage = 1;
        }
        PaginationDTO paginationDTO = new PaginationDTO();
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        if (totalCount == 0) {
            totalPage = 1;
        }
        paginationDTO.setPagination(totalPage, page);

        Integer offSet = size * (page - 1);
        List<Notification> notifications = notificationMapper.myList(id, offSet, size);//根据接收者找出所有的通知
        List<NotificationDTO> notificationDtOList = new ArrayList<>();
        for (Notification notification : notifications)
         {//循环找出通知的发布者
            User user = userMapper.findById(notification.getNotifier());
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setNotifierName(user.getName());
            if(notificationDTO.getType()==1){
                //对文章的评论,找出文章的标题
                Text text=textMapper.getById(notificationDTO.getOuterId());
                notificationDTO.setOuterTitle(text.getTitle());
            
            }
            else{
                //找出评论内容
                Comment comment=commentMapper.getById(notificationDTO.getOuterId());
                notificationDTO.setOuterId(comment.getParentID());
                notificationDTO.setOuterTitle(comment.getContent());
            }
            notificationDtOList.add(notificationDTO);
        }
        paginationDTO.setNotificationDTOs(notificationDtOList);

        return paginationDTO;
    }

}