package org.example.task.util;

import org.example.task.model.entity.Task;
import org.example.task.model.entity.User;
import org.example.task.util.enums.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportFormatter {

    public String format(User user, List<Task> tasks) {
        if (tasks.isEmpty()) {
            return "Hey " + user.getName() + ", you currently have no tasks assigned.\n";
        }

        StringBuilder report = new StringBuilder("Hello " + user.getName() + ", here's your task report:\n\n");

        report.append("Total tasks: ").append(tasks.size()).append("\n");

        // Summary by status
        for (TaskStatus status : TaskStatus.values()) {
            long count = tasks.stream().filter(t -> t.getStatus() == status).count();
            report.append("- ").append(status.name()).append(": ").append(count).append("\n");
        }

        report.append("\nTask Details:\n");

        report.append(tasks.stream()
                .map(t -> "- [" + t.getStatus() + "] " + t.getTitle() + " (Due: " + t.getDueDate() + ")")
                .collect(Collectors.joining("\n")));

        return report.toString();
    }
}

