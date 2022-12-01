package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.models.OutpatientCard;
import com.clinic.dentistry.models.Role;
import com.clinic.dentistry.models.User;
import com.clinic.dentistry.repo.OutpatientCardRepository;
import com.clinic.dentistry.repo.UserRepository;
import com.clinic.dentistry.service.RegistrationService;
import com.clinic.dentistry.service.UserService;
import com.fasterxml.jackson.core.Base64Variant;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private OutpatientCardRepository outpatientCardRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void userRegistration(User user, OutpatientCard outpatientCard){
        outpatientCardRepository.save(outpatientCard);
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setOutpatientCard(outpatientCard);
        userRepository.save(user);
    }

    @Override
    public boolean isUserInDB(User user){
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb!= null)
            return true;
        else
            return false;
    }

}
