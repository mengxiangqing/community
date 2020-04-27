package com.example.demo.mapper;

import com.example.demo.model.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface UserMapper {
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,image_url)values(#{name},#{account_id},#{token},#{gmt_create},#{gmt_modified},#{imageUrl})")
    void insert(User user);

    @Select("select * from user where token=#{token}")

    User fineByToken(@Param("token") String token);

}