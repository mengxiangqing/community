package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.TextDTO;
import com.example.demo.mapper.TextMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Text;
import com.example.demo.model.User;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextService {
    @Autowired
    private TextMapper textMapper;

    @Autowired
    private UserMapper userMapper;

    public List<TextDTO> list() {
        List<Text> texts = textMapper.list();
        List<TextDTO> textDtoList = new ArrayList<>();
        for (Text text : texts) {
            User user = userMapper.findById(text.getCreator());
            TextDTO textDTO = new TextDTO();
            BeanUtils.copyProperties(text, textDTO);
            textDTO.setUser(user);
            textDtoList.add(textDTO);
        }
        return textDtoList;
    }

}