package com.eric.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eric.app.exception.BusinessException;
import com.eric.app.dao.User;
import com.eric.app.mapper.UserMapper;
import com.eric.app.request.ReturnCode;
import com.eric.app.request.user.LoginUserReq;
import com.eric.app.request.user.LoginUserResp;
import com.eric.app.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenUtil tokenUtil;

    @GetMapping("/test")
    public void list(){
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }
    @GetMapping("/error")
    public String error(){
        return "error";
    }

    @PostMapping("/v1/user/login")
    public LoginUserResp login(@RequestBody LoginUserReq req) throws Exception {
        log.info("开始登录:" + JSONObject.toJSONString(req));
        // 先验证用户的账号密码,账号密码验证通过之后，生成Token
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .eq("username", req.getUsername())
                .eq("password", req.getPassword());
        User user = userMapper.selectOne(wrapper);
        if(user == null){
            throw  new BusinessException(ReturnCode.USERNAME_OR_PASSWORD_ERROR);
        }
        int role = user.getRoleId();
        String token = tokenUtil.getToken(req.getUsername(),role);
        LoginUserResp resp = new LoginUserResp();
        resp.setToken(token);
        return resp;
    }

}
