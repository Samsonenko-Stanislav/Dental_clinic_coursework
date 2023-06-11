package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.models.*;
import com.clinic.dentistry.repo.EmployeeRepository;
import com.clinic.dentistry.repo.OutpatientCardRepository;
import com.clinic.dentistry.repo.UserRepository;
import com.clinic.dentistry.service.RegistrationService;
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
    public void createUser(
                           User user,
                           OutpatientCard outpatientCard,
                           Employee employee){
            if (user.getOutpatientCard() != null){
                outpatientCard.setEmail(outpatientCard.getEmail());
            }

            outpatientCard.setGender(outpatientCard.getGender());
            outpatientCardRepository.save(outpatientCard);
            user.setOutpatientCard(outpatientCard);


        if (user.getEmployee() != null){
            employeeRepository.save(employee);
            user.setEmployee(employee);
        }

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.setRoles(user.getRoles());
        userRepository.save(user);
    }

    @Override
    public void editUser(User user,  Employee employee, OutpatientCard outpatientCard, Boolean changePassword){
        user.setUsername(user.getUsername());
        user.setActive(user.isActive());
        if (changePassword){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        user.setRoles(user.getRoles());

        if (employee != null && employee.getId() != null){
            user.setEmployee(employee);
        } else {
            user.setEmployee(null);
        }

        if (user.getOutpatientCard() != null){
            outpatientCard.setId(user.getOutpatientCard().getId());
            outpatientCard.setFullName(user.getOutpatientCard().getFullName());
            outpatientCard.setEmail(user.getOutpatientCard().getEmail());
            outpatientCard.setGender(user.getOutpatientCard().getGender());
            outpatientCardRepository.save(outpatientCard);
            user.setOutpatientCard(outpatientCard);
        }
        userRepository.save(user);
    }

    @Override
    public boolean isUsernameVacant(String username){
        User user = userRepository.findUserByUsername(username);
        return user == null;
    }

}
