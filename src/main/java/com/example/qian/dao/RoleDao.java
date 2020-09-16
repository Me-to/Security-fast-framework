package com.example.qian.dao;

import com.example.qian.model.RoleModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleDao {
    @Select("SELECT * FROM ROLE WHERE ID = #{id}")
    RoleModel findRoleById(int id);
}
