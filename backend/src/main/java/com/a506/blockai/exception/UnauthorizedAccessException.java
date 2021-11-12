package com.a506.blockai.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedAccessException extends ApplicationException {

    private static final String ERROR_CODE = "certificate-001";
    private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;
    private static final String MESSAGE = "본인인증에 실패하였습니다.";

    public UnauthorizedAccessException(){
        super(ERROR_CODE, HTTP_STATUS, MESSAGE);
    }
}
