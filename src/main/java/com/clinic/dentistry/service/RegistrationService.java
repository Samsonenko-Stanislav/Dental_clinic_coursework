package com.clinic.dentistry.service;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.user.RegisterForm;
import com.clinic.dentistry.dto.user.UserEditForm;
import com.clinic.dentistry.models.Employee;
import com.clinic.dentistry.models.OutpatientCard;
import com.clinic.dentistry.models.User;

public interface RegistrationService {
    ApiResponse userRegistration(RegisterForm request);

    ApiResponse createUser(RegisterForm request);

    ApiResponse editUser(Long userId, UserEditForm editForm);
    boolean isUsernameVacant(String username);
}
