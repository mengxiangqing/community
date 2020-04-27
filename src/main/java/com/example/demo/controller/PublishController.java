package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.mapper.TextMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Text;
import com.example.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import okhttp3.Cookie;

@Controller
public class PublishController {
    @Autowired
    private TextMapper textMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String DoPublish(@RequestParam("title") String title, @RequestParam("description") String description,
             HttpServletRequest request, Model model) {
        User user = null;
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        for (javax.servlet.http.Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                user = userMapper.fineByToken(token);
                if (user != null) {
                    request.getSession().setAttribute("user", user);

                }
                break;
            }
        }
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Text text = new Text();

        text.setTitle(title);
        text.setDescription(description);
        String column="abcd";
        text.setColumn(column);
        text.setCreator(user.getId());
        text.setGmtCreate(System.currentTimeMillis());
        text.setGmtModified(text.getGmtCreate());
        text.setCommentCount(1);
        text.setLikeCount(2);
        text.setViewCount(10);
        textMapper.create(text);

        return "redirect:/";
    }
}