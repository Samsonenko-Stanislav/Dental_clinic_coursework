package com.clinic.dentistry.service;

import com.clinic.dentistry.models.Employee;
import com.clinic.dentistry.models.OutpatientCard;
import com.clinic.dentistry.models.User;

import java.util.Map;

public interface RegistrationService {
    void userRegistration(User user, OutpatientCard outpatientCard);
    boolean isUserInDB(User user);
    void createUser(User user,
                    OutpatientCard outpatientCard,
                    Employee employee);
    void editUser(User user,  Employee employee, OutpatientCard outpatientCard, Boolean changePassword);
    boolean isUsernameVacant(String username);
}
