package com.ftn.nc.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(NotFoundException exeption, WebRequest webRequest){
        return handleExceptionInternal(exeption, exeption.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleBadRequestException(BadRequestException exeption, WebRequest webRequest){
        return handleExceptionInternal(exeption, exeption.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

}
