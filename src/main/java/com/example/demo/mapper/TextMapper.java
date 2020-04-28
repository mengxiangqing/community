package com.example.demo.mapper;

import java.util.List;

import com.example.demo.model.Text;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TextMapper {
    @Insert("insert into text(title,description,gmt_create,gmt_modified,creator)values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator})")
    void create(Text text);

    @Select("select * from text")
	List<Text> list();
}