package com.clinic.dentistry.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddAppointmentDTO {

    private Long doctorId;
    private LocalDateTime date;
}
