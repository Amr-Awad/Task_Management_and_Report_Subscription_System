package org.example.task.service;

import lombok.RequiredArgsConstructor;

import org.example.task.exception.apiException.AlreadySubscribedException;
import org.example.task.exception.apiException.SubscriptionNotFoundException;
import org.example.task.model.dto.subscription.SubscriptionRequest;
import org.example.task.model.dto.subscription.SubscriptionResponse;
import org.example.task.model.entity.Subscription;
import org.example.task.model.entity.User;
import org.example.task.repository.SubscriptionRepository;
import org.example.task.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionResponse subscribe(SubscriptionRequest request, Authentication auth) {
        User user = getCurrentUser(auth);

        if (subscriptionRepository.findByUser(user).isPresent()) {
            throw new AlreadySubscribedException(user.getEmail());
        }

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setFrequency(request.getFrequency());
        subscription.setReportHour(request.getReportHour());
        subscription.setStartDate(LocalDate.now());

        return toResponse(subscriptionRepository.save(subscription));
    }

    public SubscriptionResponse getSubscription(Authentication auth) {
        User user = getCurrentUser(auth);
        Subscription subscription = subscriptionRepository.findByUser(user)
                .orElseThrow(() -> new SubscriptionNotFoundException(user.getId()));

        return toResponse(subscription);
    }

    public void unsubscribe(Authentication auth) {
        User user = getCurrentUser(auth);
        if(user.getSubscription() == null) {
            throw new SubscriptionNotFoundException(user.getId());
        }

        subscriptionRepository.deleteByUser(user);

    }

    private SubscriptionResponse toResponse(Subscription subscription) {
        return new SubscriptionResponse(
                subscription.getId(),
                subscription.getStartDate(),
                subscription.getFrequency(),
                subscription.getReportHour()
        );
    }

    private User getCurrentUser(Authentication auth) {
        return ((UserDetailsImpl) auth.getPrincipal()).getUser();
    }
}
