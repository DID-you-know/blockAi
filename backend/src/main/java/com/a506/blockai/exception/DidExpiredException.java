package com.a506.blockai.exception;

import org.springframework.http.HttpStatus;

public class DidExpiredException extends ApplicationException {

    private static final String ERROR_CODE = "did-003";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "DID의 만료일이 지났습니다.";

    public DidExpiredException(){
        super(ERROR_CODE, HTTP_STATUS, MESSAGE);
    }
}
