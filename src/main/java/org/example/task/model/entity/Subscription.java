package org.example.task.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.example.task.util.enums.ReportFrequency;

import java.time.LocalDate;

/**
 * Represents a subscription entity in the system.
 * Each subscription is associated with a user and has various attributes such as frequency, start date, and end date.
 */

@NoArgsConstructor @AllArgsConstructor @Setter @Getter @ToString
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Min(0) @Max(23)
    @Column(name = "report_hour")
    private int reportHour; // Hour of the day (0–23)


    @Enumerated(EnumType.STRING)
    private ReportFrequency frequency; // DAILY, WEEKLY, MONTHLY


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    public Subscription(ReportFrequency frequency, User user, int reportHour) {
        this.frequency = frequency;
        this.user = user;
        this.reportHour = reportHour;
    }
    public Subscription(ReportFrequency frequency, User user, LocalDate startDate , int reportHour) {

        this.frequency = frequency;
        this.user = user;
        this.startDate = startDate;
        this.reportHour = reportHour;
    }
}
