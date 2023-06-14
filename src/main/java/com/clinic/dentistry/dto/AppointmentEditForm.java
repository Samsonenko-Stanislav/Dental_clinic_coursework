package com.clinic.dentistry.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentEditForm {
    private Long appointmentId;
    @NotBlank
    private String conclusion;
    @NotNull @NotEmpty
    private List<Check> checks;


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Check {
        @NotNull
        private Long goodId;
        @NotNull
        private Float price;
        @NotNull
        private Integer qty;
    }
}
