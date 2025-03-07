package com.securitygateway.loginandsignup.repository;

import com.securitygateway.loginandsignup.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByCreatedBy(String userEmail);
}
