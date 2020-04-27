package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.mapper.TextMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Text;
import com.example.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
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
    public String DoPublish(
             @RequestParam(value = "title",required = false )String title,
             @RequestParam(value = "description",required = false ) String description,
             @RequestParam(value = "title",required = false ) String column,//暂时将标题作为专栏
             HttpServletRequest request, 
             Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("column", column);
        if (title == null||title=="") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null||description=="") {
            model.addAttribute("error", "内容不能为空");
            return "publish";
        }
        if (column == null||column=="") {
            model.addAttribute("error", "板块不能为空");
            return "publish";
        }
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