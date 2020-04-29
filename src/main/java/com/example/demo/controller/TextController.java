package com.example.demo.controller;

import com.example.demo.dto.TextDTO;
import com.example.demo.service.TextService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TextController {
    @Autowired
    private TextService textService;

    @GetMapping("/text/{id}")
    public String text(@PathVariable(name = "id") Integer id,
                       Model model) {
       TextDTO textDTO= textService.getById(id);
       //增加阅读数
       textService.incView(id);
       model.addAttribute("text", textDTO);
        return "text";
    }
}