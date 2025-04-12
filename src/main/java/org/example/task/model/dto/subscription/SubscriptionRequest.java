package org.example.task.model.dto.subscription;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.task.util.enums.ReportFrequency;

import java.time.LocalDate;

/**
 * Represents a request for subscription in the system.
 * Each subscription request contains attributes such as start date and frequency.
 */

@NoArgsConstructor @AllArgsConstructor @Setter @Getter @ToString
public class SubscriptionRequest {

    @NotNull
    private LocalDate startDate;

    @NotNull
    private ReportFrequency frequency;
}

