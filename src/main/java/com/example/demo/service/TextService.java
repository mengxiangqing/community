package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.PaginationDTO;
import com.example.demo.dto.ResultDTO;
import com.example.demo.dto.TextDTO;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.TextMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Comment;
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

    @Autowired
    private CommentMapper commentMapper;

    // 找出所有的文章
    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        // 数据总数
     
        Integer totalCount = textMapper.count();
        // 计算页码
        Integer totalPage;
        if (totalCount == 0) {
            totalPage = 1;
        }
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (totalCount == 0) {
            totalPage = 1;
        }
        paginationDTO.setPagination(totalPage, page);
        // 页码数据处理
        if (page < 1) {
            page = 1;
        } else if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }
        Integer offSet = size * (page - 1);
        List<Text> texts = textMapper.list(offSet, size);
        List<TextDTO> textDtoList = new ArrayList<>();

        for (Text text : texts) {
            User user = userMapper.findById(text.getCreator());
            TextDTO textDTO = new TextDTO();
            BeanUtils.copyProperties(text, textDTO);
            textDTO.setUser(user);
            textDtoList.add(textDTO);
        }
        paginationDTO.setTexts(textDtoList);

        return paginationDTO;
    }

    // 找出该用户的所有文章
    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        Integer totalPage;
        Integer totalCount = textMapper.countById(userId);
        if (totalCount == 0)
        {
            return null;
        }
            if (totalCount % size == 0) {
                totalPage = totalCount / size;
            } else {
                totalPage = totalCount / size + 1;
            }
        if (totalCount == 0) {
            totalPage = 1;
        }
        PaginationDTO paginationDTO = new PaginationDTO();
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        if (totalCount == 0) {
            totalPage = 1;
        }
        paginationDTO.setPagination(totalPage, page);

        Integer offSet = size * (page - 1);
        List<Text> texts = textMapper.myList(userId, offSet, size);
        List<TextDTO> textDtoList = new ArrayList<>();

        for (Text text : texts) {
            User user = userMapper.findById(text.getCreator());
            TextDTO textDTO = new TextDTO();
            BeanUtils.copyProperties(text, textDTO);
            textDTO.setUser(user);
            textDtoList.add(textDTO);
        }
        paginationDTO.setTexts(textDtoList);

        return paginationDTO;
    }

    // 找到这个id的文章
    public TextDTO getById(Integer id) {
        Text text = textMapper.getById(id);// 通过id找到文章
        TextDTO textDTO = new TextDTO();// 创建传输对象
        BeanUtils.copyProperties(text, textDTO);// 复制过去
        User user = userMapper.findById(text.getCreator());// 通过作者找到用户表中的用户
        textDTO.setUser(user);

        return textDTO;
    }

    // 创建或更新文章
    public void createOrUpdate(Text text) {
        if (text.getId() == null) {
            // 创建
            text.setGmtCreate(System.currentTimeMillis());
            text.setGmtModified(text.getGmtCreate());
            textMapper.create(text);

        } else {
            // 更新
            text.setGmtModified(System.currentTimeMillis());
            textMapper.update(text);
        }
    }

    public void incView(Integer id) {
        Text text = textMapper.getById(id);
        textMapper.updateViewCount(text);
    }

    public void incComment(Integer parentId, Integer integer) {
        if (integer == 1) {
            Text text = textMapper.getById(parentId);
            textMapper.updateCommentCount(text);
        } else if (integer == 2) {
            Comment comment = commentMapper.getById(parentId);
            commentMapper.updateCommentCount(comment);
        }

    }

	public PaginationDTO listBySearch(String search, Integer page, Integer size) {
	
        PaginationDTO paginationDTO = new PaginationDTO();
        // 数据总数
     
        Integer totalCount = textMapper.countBySearch(search);
        // 计算页码
        Integer totalPage;
        if (totalCount == 0) {
            totalPage = 1;
        }
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (totalCount == 0) {
            totalPage = 1;
        }
        paginationDTO.setPagination(totalPage, page);
        // 页码数据处理
        if (page < 1) {
            page = 1;
        } else if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }
        Integer offSet = size * (page - 1);
        List<Text> texts = textMapper.listBySearch(search,offSet, size);
        List<TextDTO> textDtoList = new ArrayList<>();

        for (Text text : texts) {
            User user = userMapper.findById(text.getCreator());
            TextDTO textDTO = new TextDTO();
            BeanUtils.copyProperties(text, textDTO);
            textDTO.setUser(user);
            textDtoList.add(textDTO);
        }
        paginationDTO.setTexts(textDtoList);

        return paginationDTO;
    }

}