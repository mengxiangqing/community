package com.example.demo.mapper;

import java.util.List;

import com.example.demo.model.Notification;
import com.example.demo.model.NotificationExample;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NotificationMapper {
    long countByExample(NotificationExample example);

    int deleteByExample(NotificationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Notification record);

    int insertSelective(Notification record);

    List<Notification> selectByExample(NotificationExample example);

    Notification selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Notification record, @Param("example") NotificationExample example);

    int updateByExample(@Param("record") Notification record, @Param("example") NotificationExample example);

    int updateByPrimaryKeySelective(Notification record);

    int updateByPrimaryKey(Notification record);

    @Select("select * from notification  where receiver=#{id} ORDER BY gmt_create DESC limit #{offSet},#{size}")
	List<Notification> myList(Integer id, Integer offSet, Integer size);
}