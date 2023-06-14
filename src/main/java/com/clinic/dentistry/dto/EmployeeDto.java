package com.clinic.dentistry.dto;

import lombok.*;

import java.time.LocalTime;

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
    private int durationApp;
}
