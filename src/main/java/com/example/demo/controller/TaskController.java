package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getUserTasks(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        return taskService.getTasksByUsername(username);
    }

    @PostMapping
    public Task createTask(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Task task) {
        String username = userDetails.getUsername();
        return taskService.createTask(username, task);
    }
}
