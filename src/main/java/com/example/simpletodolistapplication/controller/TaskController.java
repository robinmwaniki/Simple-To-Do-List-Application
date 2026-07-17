package com.example.simpletodolistapplication.controller;

import com.example.simpletodolistapplication.model.Task;
import com.example.simpletodolistapplication.model.Status;
import com.example.simpletodolistapplication.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "${cors.allowed-origins:http://localhost:3000}")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable Status status) {
        return ResponseEntity.ok(taskService.getTasksByStatus(status));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Task>> getTasksByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(taskService.getTasksByCategory(categoryId));
    }

    @GetMapping("/category/{categoryId}/status/{status}")
    public ResponseEntity<List<Task>> getTasksByCategoryAndStatus(
            @PathVariable Long categoryId,
            @PathVariable Status status) {
        return ResponseEntity.ok(taskService.getTasksByCategoryAndStatus(categoryId, status));
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Task>> getTasksSortedByDueDate() {
        return ResponseEntity.ok(taskService.getTasksOrderedByDueDate());
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        try {
            Task createdTask = taskService.createTask(task, task.getCategory() != null ?
                    task.getCategory().getId() : null);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/with-category/{categoryId}")
    public ResponseEntity<Task> createTaskWithCategory(
            @Valid @RequestBody Task task,
            @PathVariable Long categoryId) {
        try {
            Task createdTask = taskService.createTask(task, categoryId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/import")
    public ResponseEntity<List<Task>> importTasks() {

        return ResponseEntity.ok(taskService.importTasks());

    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,
                                           @Valid @RequestBody Task task) {
        try {
            Task updatedTask = taskService.updateTask(id, task);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Long id,
                                                 @RequestBody Status status) {
        try {
            Task updatedTask = taskService.updateTaskStatus(id, status);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{taskId}/assign-category/{categoryId}")
    public ResponseEntity<Task> assignCategoryToTask(@PathVariable Long taskId,
                                                     @PathVariable Long categoryId) {
        try {
            Task updatedTask = taskService.assignCategory(taskId, categoryId);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{taskId}/remove-category")
    public ResponseEntity<Task> removeCategoryFromTask(@PathVariable Long taskId) {
        try {
            Task updatedTask = taskService.assignCategory(taskId, null);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchTasks(@RequestParam(required = false) String title,
                                                  @RequestParam(required = false) Status status,
                                                  @RequestParam(required = false) Long categoryId) {
        if (title != null) {

            return ResponseEntity.ok(taskService.getAllTasks());
        } else if (status != null && categoryId != null) {
            return ResponseEntity.ok(taskService.getTasksByCategoryAndStatus(categoryId, status));
        } else if (status != null) {
            return ResponseEntity.ok(taskService.getTasksByStatus(status));
        } else if (categoryId != null) {
            return ResponseEntity.ok(taskService.getTasksByCategory(categoryId));
        } else {
            return ResponseEntity.ok(taskService.getAllTasks());
        }
    }
    @PostMapping("/import-restclient")
    public ResponseEntity<List<Task>> importTasksUsingRestClient() {

        return ResponseEntity.ok(
                taskService.importTasksUsingRestClient()
        );
    }



}