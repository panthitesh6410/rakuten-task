package com.securitygateway.loginandsignup.exceptions;

import com.securitygateway.loginandsignup.exceptions.custom.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleValidationExceptions(MethodArgumentNotValidException ex)
    {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TaskNotFoundException.class)
    public Map<String,String> handleTaskNotFoundExceptions(TaskNotFoundException ex)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Error", "No Task Found!");
        return errorMap;
    }

}
