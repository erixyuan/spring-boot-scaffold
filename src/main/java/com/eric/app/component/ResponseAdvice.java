package com.eric.app.component;

import com.eric.app.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.eric.app.request.ResultData;
import com.eric.app.request.ReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 返回数据统一处理器
 */
@Slf4j
@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body instanceof String){
            try {
                return objectMapper.writeValueAsString(ResultData.success(body));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        if(body instanceof BusinessException ret){
            return ResultData.fail(ret.getRt().getCode(), ret.getRt().getMessage());
        }
        if(body instanceof Exception){
            return ResultData.fail(ReturnCode.RC500.getCode(), ReturnCode.RC500.getMessage());
        }
        if(body instanceof ResultData){
            return body;
        }
        return ResultData.success(body);
    }


}