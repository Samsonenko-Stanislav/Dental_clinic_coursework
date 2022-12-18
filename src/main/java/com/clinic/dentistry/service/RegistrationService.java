package com.clinic.dentistry.service;

import com.clinic.dentistry.models.Employee;
import com.clinic.dentistry.models.OutpatientCard;
import com.clinic.dentistry.models.User;

import java.util.Map;

public interface RegistrationService {
    void userRegistration(User user, OutpatientCard outpatientCard);
    boolean isUserInDB(User user);
    void createUser(Map<String, String> form,
                    User user,
                    OutpatientCard outpatientCard,
                    Employee employee);
    void editUser(User user, String username, String active, Employee employee, OutpatientCard outpatientCard,
                  Map<String,String> form);
    boolean isUsernameVacant(Map<String, String> form);
}
