package com.example.demo;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TaskService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTask() {
        User user = new User("testUser", "password123");
        Task task = new Task("Test Task", "This is a test task", user);

        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask("Test Task", "This is a test task", "testUser");

        assertNotNull(createdTask);
        assertEquals("Test Task", createdTask.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testGetTasksByUsername() {
        User user = new User("testUser", "password123");
        Task task1 = new Task("Task 1", "Desc 1", user);
        Task task2 = new Task("Task 2", "Desc 2", user);

        when(taskRepository.findByUser_Username("testUser")).thenReturn(Arrays.asList(task1, task2));

        List<Task> tasks = taskService.getTasksByUsername("testUser");

        assertEquals(2, tasks.size());
        verify(taskRepository, times(1)).findByUser_Username("testUser");
    }

    @Test
    void testGetTaskById() {
        User user = new User("testUser", "password123");
        Task task = new Task("Test Task", "This is a test task", user);
        task.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> retrievedTask = taskService.getTaskById(1L);

        assertTrue(retrievedTask.isPresent());
        assertEquals("Test Task", retrievedTask.get().getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteTask() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }
}
