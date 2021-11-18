package com.a506.blockai.exception;

import org.springframework.http.HttpStatus;

public class DidNotYetIssuedException extends ApplicationException {

    private static final String ERROR_CODE = "did-002";
    private static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
    private static final String MESSAGE = "아직 발급받은 DID가 없습니다.";

    public DidNotYetIssuedException(){
        super(ERROR_CODE, HTTP_STATUS, MESSAGE);
    }
}
