package com.a506.blockai.exception;

import org.springframework.http.HttpStatus;

public class DidNotFoundException extends ApplicationException {

    private static final String ERROR_CODE = "did-001";
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    private static final String MESSAGE = "해당 DID를 찾을 수 없습니다.";

    public DidNotFoundException(){
        super(ERROR_CODE, HTTP_STATUS, MESSAGE);
    }
}
