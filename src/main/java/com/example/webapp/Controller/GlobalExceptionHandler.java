package com.example.webapp.Controller;

import com.example.webapp.Exception.NotFoundException;
import com.example.webapp.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public ErrorResponse notFoundExceptionHandler(NotFoundException exception) {
        return new ErrorResponse(exception.getMessage(), 404, ":(");
    }
}
