package com.a506.blockai.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApplicationException{

    private static final String ERROR_CODE = "user-001";
    private static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
    private static final String MESSAGE = "해당하는 사용자를 찾을 수 없습니다.";

    public UserNotFoundException() {
        super(ERROR_CODE, HTTP_STATUS, MESSAGE);
    }
}
