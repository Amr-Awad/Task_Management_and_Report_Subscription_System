package org.example.task.scheduler;

import lombok.RequiredArgsConstructor;
import org.example.task.model.entity.Subscription;
import org.example.task.repository.SubscriptionRepository;
import org.example.task.repository.UserRepository;
import org.example.task.service.ReportEventPublisherService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReportScheduler {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final ReportEventPublisherService eventPublisher;

    @Scheduled(cron = "0 0 * * * *") // Every hour, on the hour
    public void publishScheduledReports() {
        int currentHour = LocalTime.now().getHour();
        LocalDate today = LocalDate.now();

        List<Subscription> subscriptions = subscriptionRepository.findAllUsersToReport(currentHour);
        subscriptions.forEach(subscription -> {
            if (shouldSendReport(subscription.getFrequency(), today, subscription.getStartDate())) {
                eventPublisher.publish(subscription.getUser());
            }
            eventPublisher.publish(subscription.getUser());
        });


    }

    private boolean shouldSendReport(org.example.task.util.enums.ReportFrequency frequency, LocalDate today, LocalDate startDate) {
        return switch (frequency) {
            case DAILY -> true;
            case WEEKLY -> today.getDayOfWeek() == startDate.getDayOfWeek();
            case MONTHLY -> today.getDayOfMonth() == startDate.getDayOfMonth();
        };
    }
}

