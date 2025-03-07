package com.securitygateway.loginandsignup.exceptions.custom;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String message){
        super(message);
    }
}
