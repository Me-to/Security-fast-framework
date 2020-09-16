package com.example.qian.dao;

import com.example.qian.model.UserModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    @Select("SELECT * FROM USER WHERE USERNAME = #{username}")
    UserModel findUserByUsername(String username);

    @Insert("insert into USER (uid,username,password,enable,role) values(#{uid},#{username},#{password},#{enable},#{role})")
    int insertUser(UserModel userModel);
}
