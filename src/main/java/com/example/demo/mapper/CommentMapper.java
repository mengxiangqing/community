package com.example.demo.mapper;

import com.example.demo.model.Comment;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment(parent_id,type,gmt_create,gmt_modified,commenter,like_count,content,comment_count)values(#{parentID},#{type},#{gmtCreate},#{gmtModified},#{commenter},#{likeCount},#{content},#{commentCount})")
    void insert(Comment comment);

}