package com.securitygateway.loginandsignup.service.implementation;

import com.securitygateway.loginandsignup.enums.TaskStatus;
import com.securitygateway.loginandsignup.exceptions.custom.TaskNotFoundException;
import com.securitygateway.loginandsignup.model.Role;
import com.securitygateway.loginandsignup.model.Task;
import com.securitygateway.loginandsignup.model.User;
import com.securitygateway.loginandsignup.repository.TaskRepository;
import com.securitygateway.loginandsignup.repository.UserRepository;
import com.securitygateway.loginandsignup.security.JwtHelper;
import com.securitygateway.loginandsignup.service.TaskService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    JwtHelper jwtHelper;

    @Autowired
    UserRepository userRepository;

    @Override
    public Task createTask(Task task, HttpServletRequest request) {
        try{
            String authorizationHeader = request.getHeader("Authorization");
            String token = authorizationHeader.substring(7);
            String userEmail = jwtHelper.extractClaims(token, Claims::getSubject);

            task.setStatus(TaskStatus.PENDING);
            task.setCreatedBy(userEmail);

            log.info("Inside TaskServiceImpl :: Saving createTask()");
            return taskRepository.save(task);
        }catch (Exception e){
            log.error("createTask() :: Error creating Task for - {}", task);
            throw new RuntimeException("Something went wrong while creating Task!");
        }
    }

    @Override
    public List<Task> fetchAllTasks(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        String token = authorizationHeader.substring(7);
        List<Task> allTasks = new ArrayList<>();
        try {

            String userEmail = jwtHelper.extractClaims(token, Claims::getSubject);
            if(userIsAdmin(userEmail)){
                log.info("fetchAllTasks() :: Fetching all tasks for ADMIN");
                allTasks = taskRepository.findAll();
            }else{
                allTasks = taskRepository.findAllByCreatedBy(userEmail);
            }
            if(CollectionUtils.isEmpty(allTasks)){
                throw new TaskNotFoundException("No Task Found for user - "+userEmail);
            }
        }catch (Exception e){
            log.error("Error - {}", e.getMessage());
        }
        return allTasks;
    }

    private boolean userIsAdmin(String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isPresent() && user.get().getRole().equals(Role.ADMIN)){
            log.info("userIsAdmin() :: User {} is ADMIN", userEmail);
            return true;
        }
        log.info("userIsAdmin() :: User {} is NOT ADMIN", userEmail);
        return false;
    }

    @Override
    public Task updateTask(int id, Task request){
        log.info("Inside TaskServiceImpl :: updateTask()");
        Optional<Task> task = taskRepository.findById(id);
        if(task.isEmpty())  throw new TaskNotFoundException("No Task Found - "+id);
        task.get().setStatus(request.getStatus());
        task.get().setTitle(request.getTitle());
        task.get().setDescription(request.getDescription());
        return taskRepository.save(task.get());
    }

    public Task deleteTask(int id){
        log.info("Inside TaskServiceImpl :: deleteTask()");
        Optional<Task> task = taskRepository.findById(id);
        if(task.isEmpty())  throw new TaskNotFoundException("No Task Found - "+id);
        taskRepository.deleteById(id);
        return task.get();
    }

}
