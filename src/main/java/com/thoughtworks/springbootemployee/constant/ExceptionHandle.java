package com.thoughtworks.springbootemployee.constant;


import com.thoughtworks.springbootemployee.Exception.CreateException;
import com.thoughtworks.springbootemployee.Exception.FindException;
import com.thoughtworks.springbootemployee.Exception.UpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler({FindException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public String handleFindException(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler({CreateException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public String handleCreateException(Exception exception){
        return exception.getMessage();
    }

    @ExceptionHandler({UpdateException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public String handleUpdateException(Exception exception){
        return exception.getMessage();
    }

}
