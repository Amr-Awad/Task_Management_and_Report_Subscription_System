package org.example.task.model.dto.subscription;

import lombok.*;
import org.example.task.util.enums.ReportFrequency;

import java.time.LocalDate;

/**
 * DTO for subscription response.
 * Contains the start date and frequency of the subscription.
 */

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
public class SubscriptionResponse {

    private Long id;
    private LocalDate startDate;
    private ReportFrequency frequency;
    private int reportHour;

}
