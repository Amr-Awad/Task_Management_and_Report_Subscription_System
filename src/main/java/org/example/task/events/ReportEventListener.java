package org.example.task.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.task.model.entity.Task;
import org.example.task.model.entity.User;
import org.example.task.repository.TaskRepository;

import org.example.task.util.ReportFormatter;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReportEventListener {

    private final TaskRepository taskRepository;
    private final ReportFormatter formatterService;
    private final JavaMailSender mailSender;

    @EventListener
    public void handleReportEvent(ReportEvent event) {
        User user = event.getUser();

        List<Task> tasks = taskRepository.findAllByUserAndDeletedFalse(user);

        String report = formatterService.format(user, tasks);


        sendEmail(user.getEmail(), "Your Task Report", report);
    }

    private void sendEmail(String to, String subject, String content) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        message.setFrom("noreply@taskmanager.com");
        message.setReplyTo("noreply@taskmanager.com");

        mailSender.send(message);

        log.info("Email sent to {} with subject: {}", to, subject);
    }
}

