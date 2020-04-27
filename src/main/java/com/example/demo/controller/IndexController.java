package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {

    @Autowired
    private UserMapper UserMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) {

        javax.servlet.http.Cookie[] cookies = request.getCookies();
        for (javax.servlet.http.Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                User user = UserMapper.fineByToken(token);
                if (user != null) {
                    request.getSession().setAttribute("user", user);

                }
                break;
            }
        }
        return "index";
    }
}