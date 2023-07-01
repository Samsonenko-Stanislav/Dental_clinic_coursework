package com.clinic.dentistry.dto;

import com.clinic.dentistry.models.WorkDay;
import lombok.*;

import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {
    private String fullName;
    private String jobTitle;
    private LocalTime workStart;
    private LocalTime workEnd;
    private Set<WorkDay> workDays;
    private Integer durationApp;
}
