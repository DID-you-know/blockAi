package com.a506.blockai.exception;

import org.springframework.http.HttpStatus;

public class EmailDuplicatedException extends ApplicationException {

    private static final String ERROR_CODE = "user-001";
    private static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;
    private static final String MESSAGE = "이미 존재하는 이메일입니다.";

    public EmailDuplicatedException(){
        super(ERROR_CODE, HTTP_STATUS, MESSAGE);
    }
}
