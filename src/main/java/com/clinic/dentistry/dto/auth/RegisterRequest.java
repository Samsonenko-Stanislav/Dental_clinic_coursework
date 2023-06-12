package com.clinic.dentistry.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "password is required")
    private String password;

    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "fullName is required")
    private String fullName;

    @Pattern(regexp = "^(male|female)$", message = "Gender must be either 'male' or 'female'.")
    private String gender;

    private String jobTitle;
    private LocalTime workStart, workEnd;
    private int durationApp;
}

