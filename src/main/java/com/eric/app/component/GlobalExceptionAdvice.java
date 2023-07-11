package com.eric.app.component;

import com.eric.app.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(value = BusinessException.class)
    public BusinessException handleBusinessException(BusinessException e){
        return e;
    }

    @ExceptionHandler(value = Exception.class)
    public Exception handleException(Exception e){
        log.error(e.toString());
        return new Exception();
    }
}
