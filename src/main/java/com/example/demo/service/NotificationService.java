package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.dto.PaginationDTO;
import com.example.demo.mapper.NotificationMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Notification;
import com.example.demo.model.NotificationExample;
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
        // NotificationExample example=new NotificationExample();
        // example.createCriteria().andReceiverEqualTo(id);
        // List<Notification> notifications=notificationMapper.selectByExampleWithRowbounds();
        List<Notification> notifications = notificationMapper.myList(id, offSet, size);//根据接收者找出所有的通知
        List<NotificationDTO> notificationDtOList = new ArrayList<>();

        for (Notification notification : notifications)
         {//循环找出通知的发布者
            User user = userMapper.findById(notification.getNotifier());
            NotificationDTO notificationDtO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDtO);
            notificationDtO.setNotifierName(user.getName());
            notificationDtOList.add(notificationDtO);
        }
        paginationDTO.setNotificationDTOs(notificationDtOList);

        return paginationDTO;
    }

}