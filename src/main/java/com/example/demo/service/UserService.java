package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        // 通过accountId查找数据库中的用户是否存在
        User dbUser = userMapper.findByAccountId(user.getAccount_id());
        if (dbUser == null) {
            // 创建用户
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userMapper.insert(user);
        } else {
            // 更新用户
            dbUser.setGmt_modified(System.currentTimeMillis());
            dbUser.setImageUrl(user.getImageUrl());
            dbUser.setToken(user.getToken());
            dbUser.setName(user.getName());
            dbUser.setAccount_id(user.getAccount_id());
            userMapper.update(dbUser);
        }

    }

}