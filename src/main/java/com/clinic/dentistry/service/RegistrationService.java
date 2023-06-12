package com.clinic.dentistry.service;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.auth.RegisterRequest;
import com.clinic.dentistry.models.Employee;
import com.clinic.dentistry.models.OutpatientCard;
import com.clinic.dentistry.models.User;

import java.util.Map;

public interface RegistrationService {
    ApiResponse userRegistration(RegisterRequest request);

    ApiResponse createUser(RegisterRequest request);

    void editUser(User user,  Employee employee, OutpatientCard outpatientCard, Boolean changePassword);
    boolean isUsernameVacant(String username);
}
