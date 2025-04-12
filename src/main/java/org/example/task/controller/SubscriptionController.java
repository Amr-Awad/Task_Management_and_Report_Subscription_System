package org.example.task.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.task.model.dto.subscription.SubscriptionRequest;
import org.example.task.model.dto.subscription.SubscriptionResponse;
import org.example.task.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionResponse> subscribe(@Valid @RequestBody SubscriptionRequest request, Authentication auth) {
        //
        return ResponseEntity.ok(subscriptionService.subscribe(request, auth));
    }

    @GetMapping
    public ResponseEntity<SubscriptionResponse> getSubscription(Authentication auth) {
        return ResponseEntity.ok(subscriptionService.getSubscription(auth));
    }

    @DeleteMapping
    public ResponseEntity<?> unsubscribe(Authentication auth) {
        subscriptionService.unsubscribe(auth);
        return ResponseEntity.ok(Map.of("message", "Unsubscribed successfully."));
    }
}

