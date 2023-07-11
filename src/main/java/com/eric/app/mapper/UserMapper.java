package com.eric.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eric.app.dao.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {

    // 原生查询例子
    @Select("select * from hl_user where username=#{username} and password=#{pwd}")
    User getUser(@Param("username") String username, @Param("pwd") String pwd);
}