package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.dto.PaginationDTO;
import com.example.demo.service.TextService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private TextService textService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "search", required = false) String search) {
        PaginationDTO pagination;

        if (search == null) {
            pagination = textService.list(page, size);
        } else {
            if (search.isEmpty()) {
                pagination = textService.list(page, size);
            } else {
    
                pagination = textService.listBySearch(search, page, size);

            }
        }
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);
        return "index";
    }
}