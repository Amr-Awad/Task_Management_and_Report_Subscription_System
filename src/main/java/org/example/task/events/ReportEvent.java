package org.example.task.events;

import lombok.Getter;
import org.example.task.model.entity.User;
import org.springframework.context.ApplicationEvent;

@Getter
public class ReportEvent extends ApplicationEvent {
    private final User user;

    public ReportEvent(User user) {
        super(user);
        this.user = user;
    }
}

