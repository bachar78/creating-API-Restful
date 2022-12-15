package com.mvcrest.spring5mvcrest.controllers.v1.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    public static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        //1. Create payload containing exception details
        ApiException apiException = new ApiException(e.getMessage(), HTTP_STATUS, ZonedDateTime.now(ZoneId.of("Z")));
        //2. Return the ResponseEntity
        return new ResponseEntity<>(apiException, HTTP_STATUS);
    }
}
