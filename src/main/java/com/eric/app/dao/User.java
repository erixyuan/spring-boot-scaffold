package com.eric.app.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("hl_user")
public class User {
    private Long id;
    private String username;
    private String nickname;
    private String avatar;
    private String phone;
    private String password;
    private Integer roleId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
