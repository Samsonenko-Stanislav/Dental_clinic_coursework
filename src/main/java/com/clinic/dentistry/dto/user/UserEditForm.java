package com.clinic.dentistry.dto.user;

import com.clinic.dentistry.models.Gender;
import com.clinic.dentistry.models.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEditForm {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private Gender gender;
    private Set<Role> roles;
    private Boolean active;
    private Long employeeId;
}
