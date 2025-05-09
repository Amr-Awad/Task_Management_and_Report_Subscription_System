package org.example.task.service;
import lombok.RequiredArgsConstructor;

import lombok.ToString;
import org.example.task.exception.apiException.InvalidTaskOperationException;
import org.example.task.exception.apiException.TaskNotFoundException;
import org.example.task.model.dto.task.TaskFilterRequest;
import org.example.task.model.dto.task.TaskRequest;
import org.example.task.model.dto.task.TaskResponse;
import org.example.task.model.entity.Task;
import org.example.task.model.entity.User;
import org.example.task.repository.TaskRepository;
import org.example.task.security.UserDetailsImpl;
import org.example.task.util.enums.TaskStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskResponse createTask(TaskRequest request, Authentication auth) {
        User user = getCurrentUser(auth);

        if(request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new InvalidTaskOperationException("Title cannot be null or empty.");
        }
        if(request.getStartDate() == null || request.getDueDate() == null) {
            throw new InvalidTaskOperationException("Start date and due date cannot be null.");
        }
        if(request.getStartDate().isAfter(request.getDueDate() )) {
            throw new InvalidTaskOperationException("Start date cannot be after due date.");
        }

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription() != null ? request.getDescription() : "");
        task.setStartDate(request.getStartDate());
        task.setDueDate(request.getDueDate());
        task.setCompletionDate(null);
        task.setStatus(TaskStatus.PENDING);
        task.setDeleted(false);
        task.setDeletedAt(null);

        // Save the task
        TaskResponse taskResponse = toResponse(taskRepository.save(task));

        // Set the user
        taskRepository.addUserToTask(user.getId(), taskResponse.getId());

        return taskResponse;
    }

    public TaskResponse getTaskByIdForUser(Long id, Authentication auth) {
        User user = getCurrentUser(auth);

        if(!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }

        taskRepository.updateTaskStatusToOverdueById(id, LocalDate.now());

        Task task =taskRepository.findActiveById(id).orElseThrow(() -> new TaskNotFoundException(id));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new InvalidTaskOperationException("You do not own this task.");
        }


        return toResponse(task);
    }

    public List<TaskResponse> getAllTasksForUser(Authentication auth) {
        User user = getCurrentUser(auth);

        taskRepository.updateTaskStatusToOverdue(LocalDate.now());

        List<Task> tasks = taskRepository.findAllByUserAndDeletedFalse(user);

        return tasks.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<TaskResponse> filterTasks(TaskFilterRequest request, Authentication auth) {
        User user = getCurrentUser(auth);

        if(request.getFromDate().isAfter( request.getToDate() )) {
            throw new InvalidTaskOperationException("Start date cannot be after due date.");
        }
        List<Task> tasks = taskRepository.findTasksByConditionalParameters(
                user,
                request.getFromDate(),
                request.getToDate(),
                request.getStatus()
        );

        return tasks.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public TaskResponse updateTask(Long id, TaskRequest request, Authentication auth) {
        User user = getCurrentUser(auth);
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new InvalidTaskOperationException("You do not own this task.");
        }

        if (task.getDeleted()) {
            throw new InvalidTaskOperationException("This task is deleted and cannot be updated.");
        }

        //set attributes if they exists in lambda
        task.setTitle(request.getTitle() != null ? request.getTitle() : task.getTitle());
        task.setDescription(request.getDescription() != null ? request.getDescription() : task.getDescription());
        task.setStartDate(request.getStartDate() != null ? request.getStartDate() : task.getStartDate());
        task.setDueDate(request.getDueDate() != null ? request.getDueDate() : task.getDueDate());

        if (request.getStartDate() != null && request.getDueDate() != null && request.getStartDate().isAfter(request.getDueDate())) {
            throw new InvalidTaskOperationException("Start date cannot be after due date.");
        }

        return toResponse(taskRepository.save(task));
    }

    public TaskResponse markTaskAsCompleted(Long id, Authentication auth) {
        User user = getCurrentUser(auth);

        Task task = taskRepository.findActiveById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new InvalidTaskOperationException("You do not own this task.");
        }

        task.setStatus(TaskStatus.COMPLETED);
        task.setCompletionDate(LocalDate.now());

        return toResponse(taskRepository.save(task));
    }

    public void deleteTask(Long id, Authentication auth) {
        User user = getCurrentUser(auth);
        Task task = taskRepository.findActiveById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new InvalidTaskOperationException("You do not own this task.");
        }

        if(task.getDeleted()) {
            throw new InvalidTaskOperationException("This task is already deleted.");
        }

        task.setDeleted(true);
        task.setDeletedAt(LocalDateTime.now());
        taskRepository.save(task);
    }

    public int deleteTasksByDateRange(LocalDate from, LocalDate to, Authentication auth) {
        User user = getCurrentUser(auth);
        List<Task> tasks = taskRepository.findTasksByConditionalParameters(user, from, to, null);

        if (tasks.isEmpty()) {
            throw new InvalidTaskOperationException("No tasks found in the specified date range.");
        }

        tasks.forEach(task -> task.setDeleted(true));
        tasks.forEach(task -> task.setDeletedAt(LocalDateTime.now()));
        taskRepository.saveAll(tasks);
        return tasks.size();
    }

    public List<TaskResponse> restoreLastDeleted(Authentication auth) {
        User user = getCurrentUser(auth);

        List<Task> deletedTasks = taskRepository.findAllDeletedByUserOrderByDeletedAtDesc(user);
        if (deletedTasks.isEmpty()) {
            throw new InvalidTaskOperationException("No deleted tasks to restore.");
        }

        // Step 1: get the most recent deletedAt timestamp
        LocalDateTime latestTime = deletedTasks.get(0).getDeletedAt();

        // Step 2: find all tasks deleted at that same time (i.e., batch)
        List<Task> toRestore = deletedTasks.stream()
                .filter(task -> task.getDeletedAt().equals(latestTime))
                .collect(Collectors.toList());

        // Step 3: restore them
        toRestore.forEach(t -> {
            t.setDeleted(false);
            t.setDeletedAt(null);
        });

        taskRepository.saveAll(toRestore);

        return toRestore.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public void deleteOldSoftDeletedTasks() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(1);
        List<Task> toDelete = taskRepository.findByDeletedTrueAndDeletedAtBefore(cutoff);

        taskRepository.deleteAll(toDelete);
        System.out.println("🧹 Cleaned up " + toDelete.size() + " old soft-deleted tasks.");
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStartDate(),
                task.getDueDate(),
                task.getCompletionDate(),
                task.getStatus()
        );
    }

    private User getCurrentUser(Authentication auth) {
        return ((UserDetailsImpl) auth.getPrincipal()).getUser();
    }
}

