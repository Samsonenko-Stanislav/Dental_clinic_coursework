package com.clinic.dentistry.dto.auth;

import com.clinic.dentistry.models.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Set<Role> roles;
}
