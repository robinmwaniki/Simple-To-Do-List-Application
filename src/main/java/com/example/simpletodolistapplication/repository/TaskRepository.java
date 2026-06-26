package com.example.simpletodolistapplication.repository;

import com.example.simpletodolistapplication.model.Task;
import com.example.simpletodolistapplication.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(Status status);
    List<Task> findByCategoryId(Long categoryId);
    List<Task> findByCategoryIdAndStatus(Long categoryId, Status status);

    @Query("SELECT t FROM Task t WHERE t.dueDate IS NOT NULL ORDER BY t.dueDate ASC")
    List<Task> findAllOrderByDueDate();

    Optional<Task> findByTitle(String title);
}