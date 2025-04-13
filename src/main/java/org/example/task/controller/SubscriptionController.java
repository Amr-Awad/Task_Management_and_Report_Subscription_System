package org.example.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.task.exception.ErrorResponse;
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
@Tag(name = "Subscription", description = "Task report subscription management APIs")
@SecurityRequirement(name = "bearerAuth")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @Operation(summary = "Create subscription",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Subscription created"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "409", description = "Already subscribed",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<SubscriptionResponse> subscribe(
            @Valid @RequestBody SubscriptionRequest request,
            Authentication auth) {
        return ResponseEntity.ok(subscriptionService.subscribe(request, auth));
    }

    @GetMapping
    @Operation(summary = "Get subscription",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Subscription details"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Subscription not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<SubscriptionResponse> getSubscription(Authentication auth) {
        return ResponseEntity.ok(subscriptionService.getSubscription(auth));
    }

    @DeleteMapping
    @Operation(summary = "Cancel subscription",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Unsubscribed successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Subscription not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<Map<String, String>> unsubscribe(Authentication auth) {
        subscriptionService.unsubscribe(auth);
        return ResponseEntity.ok(Map.of("message", "Unsubscribed successfully."));
    }
}