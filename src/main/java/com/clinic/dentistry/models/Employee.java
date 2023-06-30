package com.clinic.dentistry.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String jobTitle;
    private LocalTime workStart;
    private LocalTime workEnd;
    private int durationApp;

    public LocalTime getWorkStart() {
        return workStart != null ? workStart : LocalTime.of(9, 0);
    }

    public LocalTime getWorkEnd() {
        return workEnd != null ? workEnd : LocalTime.of(18, 0);
    }

    @Override
    public String toString() {
        return fullName + " " + jobTitle;
    }
}
