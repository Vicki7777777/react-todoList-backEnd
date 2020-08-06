package com.thoughtworks.react_todoList.constant;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public String handleException(Exception exception){
        return exception.getMessage();
    }

}
