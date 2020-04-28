package com.example.demo.controller;

import javax.servlet.http.Cookie;

import com.example.demo.dto.PaginationDTO;
import com.example.demo.mapper.UserMapper;
import javax.servlet.http.HttpServletRequest;
import com.example.demo.model.User;
import com.example.demo.service.TextService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper UserMapper;
    @Autowired
    private TextService textService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, Model model, HttpServletRequest request, 
    @RequestParam(name = "page", defaultValue = "1") Integer page,
    @RequestParam(name = "size", defaultValue = "5") Integer size) {
        Cookie[] cookies = request.getCookies();
        User user=null;
        if (cookies != null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = UserMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        if(user==null)
        {
            return "redirect:/";
        }
        if ("texts".equals(action)) {
            model.addAttribute("section", "texts");
            model.addAttribute("sectionName", "我的文章");

        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }
        PaginationDTO paginationDTO=textService.list(user.getId(),page, size);
        model.addAttribute("pagination",paginationDTO);
        return "profile";

    }
}