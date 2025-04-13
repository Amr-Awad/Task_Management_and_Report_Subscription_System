package org.example.task.model.dto.subscription;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.task.util.enums.ReportFrequency;

@NoArgsConstructor @AllArgsConstructor @Setter @Getter @ToString
@Schema(description = "Subscription request details")
public class SubscriptionRequest {

    @NotNull
    @Schema(description = "Report frequency", implementation = ReportFrequency.class, required = true)
    private ReportFrequency frequency;

    @NotNull
    @Schema(description = "Hour of day to send report (0-23)", example = "9", required = true, minimum = "0", maximum = "23")
    private int reportHour;
}