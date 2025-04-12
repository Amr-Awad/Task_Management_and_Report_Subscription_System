package org.example.task.repository;
import org.example.task.model.entity.Task;
import org.example.task.model.entity.User;
import org.example.task.util.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Override findById to only return non-deleted tasks
    @Query("SELECT t FROM Task t WHERE t.id = :id AND t.deleted = false")
    Optional<Task> findActiveById(@Param("id") Long id);

    // For filtering only non-deleted tasks
    @Query("SELECT t FROM Task t WHERE " +
            "t.deleted = false AND " +
            "(:user IS NULL OR t.user = :user) AND " +
            "(:from IS NULL OR t.startDate >= :from) AND " +
            "(:to IS NULL OR t.dueDate <= :to) AND " +
            "(:status IS NULL OR t.status = :status)")
    List<Task> findTasksByConditionalParameters(
            @Param("user") User user,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            @Param("status") TaskStatus status);

    // Get last deleted task for a user
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.deleted = true ORDER BY t.id DESC")
    List<Task> findLastDeletedByUser(@Param("user") User user);

    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.deleted = true ORDER BY t.deletedAt DESC")
    List<Task> findAllDeletedByUserOrderByDeletedAtDesc(@Param("user") User user);

    List<Task> findByDeletedTrueAndDeletedAtBefore(LocalDateTime cutoff);
}
