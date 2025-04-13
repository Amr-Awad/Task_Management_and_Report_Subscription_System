package org.example.task.model.dto.subscription;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.example.task.util.enums.ReportFrequency;

import java.time.LocalDate;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
@Schema(description = "Subscription details response")
public class SubscriptionResponse {

    @Schema(description = "Subscription ID", example = "1")
    private Long id;

    @Schema(description = "Subscription start date", example = "2025-01-01")
    private LocalDate startDate;

    @Schema(description = "Report frequency", implementation = ReportFrequency.class)
    private ReportFrequency frequency;

    @Schema(description = "Hour of day reports are sent", example = "9")
    private int reportHour;
}