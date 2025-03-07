package com.securitygateway.loginandsignup.service;

import com.securitygateway.loginandsignup.model.Task;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    Task createTask(Task task, HttpServletRequest request);

    List<Task> fetchAllTasks(HttpServletRequest request);

    Task updateTask(int id, Task task);
    Task deleteTask(int id);
}
