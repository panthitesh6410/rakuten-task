package com.securitygateway.loginandsignup.model;

import com.securitygateway.loginandsignup.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "task_Seq", sequenceName = "task_sequence", allocationSize = 1)
    private int id;

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private TaskStatus status;
    private String createdBy;       // email of user
}
