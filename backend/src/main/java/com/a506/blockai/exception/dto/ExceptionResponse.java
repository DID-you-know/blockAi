package com.a506.blockai.exception.dto;

public class ExceptionResponse {

    private String errorCode;

    protected ExceptionResponse() {}

    public ExceptionResponse(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public static ExceptionResponse of(String message) {
        return new ExceptionResponse(message);
    }
}
