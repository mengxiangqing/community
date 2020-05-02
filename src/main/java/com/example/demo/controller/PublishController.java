package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.dto.TextDTO;
import com.example.demo.model.Text;
import com.example.demo.model.User;
import com.example.demo.service.TextService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PublishController {

    @Autowired
    private TextService textService;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id, Model model) {

        TextDTO text = textService.getById(id);
        model.addAttribute("title", text.getTitle());// 为了回显
        model.addAttribute("description", text.getDescription());
        model.addAttribute("tag", text.getTag());
        model.addAttribute("id", text.getId());
        return "publish";
    }

    @PostMapping("/publish")
    public String DoPublish(@RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag, 
            @RequestParam(value = "id", required = false) Integer id,
            HttpServletRequest request, 
            Model model) {
        model.addAttribute("title", title);// 为了回显
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "内容不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "板块不能为空");
            return "publish";
        }
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Text text = new Text();
        text.setTitle(title);
        text.setDescription(description);
        text.setTag(tag);
        text.setCreator(user.getId());
        text.setCommentCount(1);
        text.setLikeCount(2);
        text.setViewCount(10);
        text.setId(id);
        textService.createOrUpdate(text);

        return "redirect:/";
    }
}