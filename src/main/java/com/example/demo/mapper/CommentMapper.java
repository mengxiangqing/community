package com.example.demo.mapper;

import java.util.List;

import com.example.demo.model.Comment;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment(parent_id,type,gmt_create,gmt_modified,commenter,like_count,content,comment_count)values(#{parentID},#{type},#{gmtCreate},#{gmtModified},#{commenter},#{likeCount},#{content},#{commentCount})")
    void insert(Comment comment);

    @Select("select * from comment where parent_id=#{id}")
	List<Comment> listByTextId(Integer id);

}