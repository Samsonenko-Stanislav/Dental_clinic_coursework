package com.clinic.dentistry.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {
    private int status;
    private String message;
    private Object data;
}
