package org.example.task.model.dto.subscription;

import lombok.*;
import org.example.task.util.enums.ReportFrequency;

import java.time.LocalDate;

/**
 * DTO for subscription response.
 * Contains the start date and frequency of the subscription.
 */

@NoArgsConstructor @AllArgsConstructor @Setter @Getter @ToString
public class SubscriptionResponse {

    private LocalDate startDate;
    private ReportFrequency frequency;
}
