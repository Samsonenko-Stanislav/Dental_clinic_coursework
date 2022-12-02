package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.models.*;
import com.clinic.dentistry.repo.EmployeeRepository;
import com.clinic.dentistry.repo.OutpatientCardRepository;
import com.clinic.dentistry.repo.UserRepository;
import com.clinic.dentistry.service.RegistrationService;
import com.clinic.dentistry.service.UserService;
import com.fasterxml.jackson.core.Base64Variant;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private OutpatientCardRepository outpatientCardRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

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

    @Override
    public void createUser(Map<String, String> form,
                           User user,
                           OutpatientCard outpatientCard,
                           Employee employee){
        if (form.get("USER") != null && form.get("USER").equals("on")){
            outpatientCard.setEmail(form.get("email"));
            if (form.get("MALE") != null){
                outpatientCard.setGender(Gender.MALE);
            }
            if (form.get("FEMALE") != null) {
                outpatientCard.setGender(Gender.FEMALE);
            }
            outpatientCardRepository.save(outpatientCard);
            user.setOutpatientCard(outpatientCard);
        }

        if (form.get("DOCTOR") != null && form.get("DOCTOR").equals("on")){
            employeeRepository.save(employee);
            user.setEmployee(employee);
        }

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        Set<Role> user_roles = new HashSet<>();
        for (String key : form.keySet()) {
            if (roles.contains(key)){
                user_roles.add(Role.valueOf(key));
            }
        }
        user.setRoles(user_roles);

        userRepository.save(user);
    }

    @Override
    public void editUser(User user, String username, String active, Employee employee, Map<String, String> form){
        user.setUsername(username);
        if (active != null){
            user.setActive(true);
        } else {
            user.setActive(false);
        }


        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }

        if (employee != null && employee.getId() != null){
            user.setEmployee(employee);
        } else {
            user.setEmployee(null);
        }
        userRepository.save(user);
    }

}
