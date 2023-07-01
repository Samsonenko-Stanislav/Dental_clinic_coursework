package com.clinic.dentistry.dto.user;

import com.clinic.dentistry.models.Gender;
import com.clinic.dentistry.models.Role;
import com.clinic.dentistry.models.WorkDay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterForm {
    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "password is required")
    private String password;

    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "fullName is required")
    private String fullName;

    @NotNull
    private Gender gender;

    private Set<Role> roles;
    private String jobTitle;
    private LocalTime workStart, workEnd;
    private Set<WorkDay> workDays;
    private int durationApp;
}

