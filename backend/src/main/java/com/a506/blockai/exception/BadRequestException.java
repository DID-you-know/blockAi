package com.a506.blockai.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApplicationException {

    private static final String ERROR_CODE = "request-001";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "요청 데이터가 잘못되었습니다.";

    public BadRequestException() {
        super(ERROR_CODE, HTTP_STATUS, MESSAGE);
    }
}
