package com.example.demo.mapper;

import java.util.List;

import com.example.demo.model.Text;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TextMapper {
    @Insert("insert into text(title,description,gmt_create,gmt_modified,creator)values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator})")
    void create(Text text);

    @Select("select * from text limit #{offSet},#{size}")
    List<Text> list(Integer offSet, Integer size);
    
    @Select("select count(1) from text")
    Integer count();

    @Select("select * from text where creator=#{userId} limit #{offSet},#{size}")
    List<Text> myList(Integer userId, Integer offSet, Integer size);

    @Select("select count(1) from text where creator=#{userId}")
	Integer countById(Integer userId);

    @Select("select * from text where id=#{id}")
	Text getById(Integer id);

    @Update("update text set title=#{title},description=#{description},gmt_modified=#{gmtModified} where id=#{id} ")
	void update(Text text);
    
    


}