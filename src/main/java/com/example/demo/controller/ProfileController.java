package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.dto.PaginationDTO;
import com.example.demo.model.User;
import com.example.demo.service.NotificationService;
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
    private TextService textService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, Model model, HttpServletRequest request,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        if ("texts".equals(action)) {
            model.addAttribute("section", "texts");
            model.addAttribute("sectionName", "我的文章");
            PaginationDTO paginationDTO = textService.list(user.getId(), page, size);
            if (paginationDTO == null) {
                return "error";
            }
            model.addAttribute("pagination", paginationDTO);

        } else if ("replies".equals(action)) {
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);//找到回复这个用户的通知
           
            model.addAttribute("pagination", paginationDTO);
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

        return "profile";

    }
}