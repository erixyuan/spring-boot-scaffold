package com.eric.app.component;

import com.eric.app.exception.TokenAuthExpiredException;
import com.eric.app.request.ReturnCode;
import com.eric.app.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * 授权检查拦截器的实现
 */
@Slf4j
@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenUtil tokenUtil;
    @Value("${jwt.token.refreshTime}")
    private Long refreshTime;
    @Value("${jwt.token.expiresTime}")
    private Long expiresTime;

    /**
     * 权限认证的拦截操作.
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        log.debug("=======进入拦截器========");
        // 如果不是映射到方法直接通过,可以访问资源.
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        //为空就返回错误
        String token = httpServletRequest.getHeader("token");
        if (null == token || "".equals(token.trim())) {
            return false;
        }
        log.debug("==============token:" + token);
        Map<String, String> map = tokenUtil.parseToken(token);
        String userId = map.get("userId");
        int userRole = Integer.parseInt(map.get("userRole"));
        long timeOfUse = System.currentTimeMillis() - Long.parseLong(map.get("timeStamp"));
        //1.判断 token 是否过期
        if (timeOfUse < refreshTime) {
            log.info("token验证成功");
            return true;
        }
        //超过token刷新时间，刷新 token
        else if (timeOfUse >= refreshTime && timeOfUse < expiresTime) {
            httpServletResponse.setHeader("token", tokenUtil.getToken(userId,userRole));
            log.info("token刷新成功");
            return true;
        }
        //token过期就返回 token 无效.
        else {
            throw new TokenAuthExpiredException(ReturnCode.INVALID_TOKEN);
        }
    }
}
