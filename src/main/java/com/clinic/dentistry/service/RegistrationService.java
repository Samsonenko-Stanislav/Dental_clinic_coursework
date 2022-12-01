package com.clinic.dentistry.service;

import com.clinic.dentistry.models.OutpatientCard;
import com.clinic.dentistry.models.User;

public interface RegistrationService {
    void userRegistration(User user, OutpatientCard outpatientCard);
    boolean isUserInDB(User user);
}
