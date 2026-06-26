package com.example.simpletodolistapplication.service;

import com.example.simpletodolistapplication.model.Task;
import com.example.simpletodolistapplication.model.Status;
import com.example.simpletodolistapplication.model.Category;
import com.example.simpletodolistapplication.repository.TaskRepository;
import com.example.simpletodolistapplication.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> getTasksByStatus(Status status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> getTasksByCategory(Long categoryId) {
        return taskRepository.findByCategoryId(categoryId);
    }

    public List<Task> getTasksByCategoryAndStatus(Long categoryId, Status status) {
        return taskRepository.findByCategoryIdAndStatus(categoryId, status);
    }

    public List<Task> getTasksOrderedByDueDate() {
        return taskRepository.findAllOrderByDueDate();
    }

    public Task createTask(Task task, Long categoryId) {
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
            task.setCategory(category);
        }
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        task.setPriority(taskDetails.getPriority());
        task.setDueDate(taskDetails.getDueDate());

        if (taskDetails.getCategory() != null) {
            Category category = categoryRepository.findById(taskDetails.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + taskDetails.getCategory().getId()));
            task.setCategory(category);
        }

        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    public Task updateTaskStatus(Long id, Status status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        task.setStatus(status);
        return taskRepository.save(task);
    }

    public Task assignCategory(Long taskId, Long categoryId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

        Category category = null;
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
        }

        task.setCategory(category);
        return taskRepository.save(task);
    }
}