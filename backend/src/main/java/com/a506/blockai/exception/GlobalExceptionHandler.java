package com.a506.blockai.exception;

import com.a506.blockai.exception.dto.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ExceptionResponse> applicationException(ApplicationException e) {
        logger.info("Application Exception", e);

        ExceptionResponse response = ExceptionResponse.of(e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> Exception(ApplicationException e) {
        logger.info("Unhandled Exception", e);

        ExceptionResponse response = ExceptionResponse.of(e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(response);
    }
}
