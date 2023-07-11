package com.eric.app.exception;

import com.eric.app.request.ReturnCode;
import lombok.Data;

@Data
public class BusinessException extends Exception {
    private ReturnCode rt;

    public BusinessException(ReturnCode rt) {
        this.rt = rt;
    }
}
