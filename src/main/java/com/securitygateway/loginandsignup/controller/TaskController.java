package com.securitygateway.loginandsignup.controller;

import com.securitygateway.loginandsignup.model.Task;
import com.securitygateway.loginandsignup.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task, HttpServletRequest request){
        log.info("Inside TaskController :: createTask()");
        try{
            return new ResponseEntity<>(taskService.createTask(task, request), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(HttpServletRequest request){
        log.info("Inside TaskController :: getAllTasks()");
        try{
            return new ResponseEntity<>(taskService.fetchAllTasks(request), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody Task task){
        log.info("Inside TaskController :: updateTask()");
        try{
            return new ResponseEntity<>(taskService.updateTask(id, task), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<Task> deleteTask(@PathVariable int id){
        log.info("Inside TaskController :: deleteTask()");
        try{
            return new ResponseEntity<>(taskService.deleteTask(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
