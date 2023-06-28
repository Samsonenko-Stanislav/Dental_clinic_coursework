package com.clinic.dentistry.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddAppointmentDTO {
    @Override
    public String toString() {
        return "AddAppointmentDTO{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }

    private Long id;
    private String date;
}
