package com.clinic.dentistry.service;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.user.RegisterForm;
import com.clinic.dentistry.dto.user.UserEditForm;
import com.clinic.dentistry.models.User;

import java.util.HashMap;

public interface RegistrationService {
    ApiResponse userRegistration(RegisterForm request);

    ApiResponse createUser(RegisterForm request);

    ApiResponse editUser(Long userId, UserEditForm editForm);
    boolean isUsernameVacant(String username);

    HashMap<String, Object> getUserList();

    HashMap<String, Object> getUsr(Long userId);

    HashMap<String, Object> getMyProfile(User user);
}
