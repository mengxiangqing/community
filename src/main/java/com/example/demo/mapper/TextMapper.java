package com.example.demo.mapper;

import java.util.List;

import com.example.demo.model.Text;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TextMapper {
    // 创建文章
    @Insert("insert into text(title,description,gmt_create,gmt_modified,creator)values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator})")
    void create(Text text);

    // 为了分页展示，offset偏移量，size尺寸
    @Select("select * from text limit #{offSet},#{size}")
    List<Text> list(Integer offSet, Integer size);

    // 为了找出一共有多少数据
    @Select("select count(1) from text")
    Integer count();

    // 分页展示我的文章
    @Select("select * from text where creator=#{userId} limit #{offSet},#{size}")
    List<Text> myList(Integer userId, Integer offSet, Integer size);

    // 找出我的文章总数
    @Select("select count(1) from text where creator=#{userId}")
    Integer countById(Integer userId);

    // 寻找文章
    @Select("select * from text where id=#{id}")
    Text getById(Integer id);

    // 更新文章
    @Update("update text set title=#{title},description=#{description},gmt_modified=#{gmtModified} where id=#{id} ")
    void update(Text text);

    // 更新阅读数
    @Update("update text set view_count=view_count+1 where id=#{id} ")
    void updateViewCount(Text text);
    //更新评论数
    @Update("update text set comment_count=comment_count+1 where id=#{id} ")
	void updateCommentCount(Text text);
}