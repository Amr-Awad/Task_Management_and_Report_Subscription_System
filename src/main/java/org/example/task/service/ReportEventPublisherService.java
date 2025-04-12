package org.example.task.service;

import lombok.RequiredArgsConstructor;
import org.example.task.events.ReportEvent;
import org.example.task.model.entity.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportEventPublisherService {
    private final ApplicationEventPublisher eventPublisher;

    public void publish(User user) {
        eventPublisher.publishEvent(new ReportEvent(user));
    }
}

