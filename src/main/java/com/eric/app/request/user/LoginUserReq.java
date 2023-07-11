package com.eric.app.request.user;

import lombok.Data;

@Data
public class LoginUserReq {
    private String username;
    private String password;
}