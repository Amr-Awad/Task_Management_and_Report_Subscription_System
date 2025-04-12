package org.example.task.scheduler;

import lombok.RequiredArgsConstructor;
import org.example.task.model.entity.Task;
import org.example.task.repository.TaskRepository;
import org.example.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskCleanupScheduler {

    @Autowired
    private final TaskService taskService;

    @Scheduled(cron = "0 0 3 * * *") // Runs daily at 3 AM
    public void deleteOldSoftDeletedTasks() {
        taskService.deleteOldSoftDeletedTasks();
    }
}

