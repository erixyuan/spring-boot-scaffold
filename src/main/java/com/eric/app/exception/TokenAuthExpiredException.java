package com.eric.app.exception;

import com.eric.app.request.ReturnCode;

public class TokenAuthExpiredException extends BusinessException{

    public TokenAuthExpiredException(ReturnCode rt) {
        super(rt);
    }
}
