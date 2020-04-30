package com.example.demo.mapper;

import java.util.List;

import com.example.demo.model.Comment;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment(parent_id,type,gmt_create,gmt_modified,commenter,like_count,content,comment_count)values(#{parentID},#{type},#{gmtCreate},#{gmtModified},#{commenter},#{likeCount},#{content},#{commentCount})")
    void insert(Comment comment);


    @Select("select * from comment  where parent_id=#{id} and type=#{comment} ORDER BY gmt_modified DESC")
	List<Comment> listByTargetId(Integer id, Integer comment);

    @Select("select *  from comment  where id=#{parentId} ORDER BY gmt_modified DESC")
	Comment getById(Integer parentId);

    @Update("update comment set comment_count=comment_count+1 where id=#{id} ")
	void updateCommentCount(Comment comment);

}