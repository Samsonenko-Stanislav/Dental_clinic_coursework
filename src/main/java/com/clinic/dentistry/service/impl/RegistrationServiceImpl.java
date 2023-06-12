package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.auth.RegisterRequest;
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
    public ApiResponse userRegistration(RegisterRequest request) {
        if (this.isUserInDB(request)) {
            return ApiResponse.builder()
                    .status(400)
                    .message("Пользователь с такими данными уже существует!")
                    .build();
        }

        OutpatientCard outpatientCard = OutpatientCard.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .gender(request.getGender().equalsIgnoreCase("male") ? Gender.MALE : Gender.FEMALE)
                .build();
        outpatientCardRepository.save(outpatientCard);

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .roles(Collections.singleton(Role.USER))
                .outpatientCard(outpatientCard)
                .build();
        userRepository.save(user);

        return ApiResponse.builder()
                .status(200)
                .message("Регистрация прошла успешно")
                .build();
    }

    @Override
    public ApiResponse createUser(RegisterRequest request) {
        if (this.isUserInDB(request)) {
            return ApiResponse.builder()
                    .status(400)
                    .message("Пользователь с такими данными уже существует!")
                    .build();
        }

        Employee employee = Employee.builder()
                .fullName(request.getFullName())
                .jobTitle(request.getJobTitle())
                .workStart(request.getWorkStart())
                .workEnd(request.getWorkEnd())
                .durationApp(request.getDurationApp())
                .build();
        employeeRepository.save(employee);

        OutpatientCard outpatientCard = OutpatientCard.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .gender(request.getGender().equalsIgnoreCase("male") ? Gender.MALE : Gender.FEMALE)
                .build();
        outpatientCardRepository.save(outpatientCard);

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .roles(Collections.singleton(Role.USER))
                .outpatientCard(outpatientCard)
                .employee(employee)
                .build();
        userRepository.save(user);

        return ApiResponse.builder()
                .status(200)
                .message("Регистрация прошла успешно")
                .build();

//        Set<String> roles = Arrays.stream(Role.values())
//                .map(Role::name)
//                .collect(Collectors.toSet());
//
//        user.setRoles(user.getRoles());
//        userRepository.save(user);
    }

    @Override
    public void editUser(User user, Employee employee, OutpatientCard outpatientCard, Boolean changePassword) {
        user.setUsername(user.getUsername());
        user.setActive(user.isActive());
        if (changePassword) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        user.setRoles(user.getRoles());

        if (employee != null && employee.getId() != null) {
            user.setEmployee(employee);
        } else {
            user.setEmployee(null);
        }

        if (user.getOutpatientCard() != null) {
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
    public boolean isUsernameVacant(String username) {
        User user = userRepository.findUserByUsername(username);
        return user == null;
    }

    private boolean isUserInDB(RegisterRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (user != null) {
            return true;
        }

        OutpatientCard outpatientCard = outpatientCardRepository.findByEmail(request.getEmail());
        if (outpatientCard != null) {
            return true;
        }

        return false;
    }

}
