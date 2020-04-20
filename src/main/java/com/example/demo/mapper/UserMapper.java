package com.example.demo.mapper;

import com.example.demo.model.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
@Mapper
public interface UserMapper {
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified)values(#{name},#{account_id},#{token},#{gmt_create},#{gmt_modified})")
    void insert(User user);
}