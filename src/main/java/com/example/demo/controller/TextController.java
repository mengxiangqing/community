package com.example.demo.controller;

import java.util.List;

import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.TextDTO;
import com.example.demo.model.Comment;
import com.example.demo.service.CommentService;
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
    @Autowired
    private CommentService commentService;

    @GetMapping("/text/{id}")
    public String text(@PathVariable(name = "id") Integer id,
                       Model model) {
       TextDTO textDTO= textService.getById(id);
       List<CommentDTO> comments = commentService.listByTargetId(id,1);
       //增加阅读数
       textService.incView(id);
       model.addAttribute("text", textDTO);
       model.addAttribute("comments", comments);
       for (CommentDTO commentDTO : comments) {
           Integer commentId=commentDTO.getId();
           List<CommentDTO> commentChildLists=commentService.listByTargetId(commentId, 2);
           model.addAttribute("commentDTOS", commentChildLists);
       }
        return "text";
    }
}