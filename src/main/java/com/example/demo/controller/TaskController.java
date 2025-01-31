package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public Task createTask(@RequestParam String title, @RequestParam String description, @RequestParam String username) {
        return taskService.createTask(title, description, username);
    }

    @GetMapping("/{username}")
    public List<Task> getTasksByUsername(@PathVariable String username) {
        return taskService.getTasksByUsername(username);
    }
}
