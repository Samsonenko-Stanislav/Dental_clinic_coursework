package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.user.RegisterForm;
import com.clinic.dentistry.dto.user.UserEditForm;
import com.clinic.dentistry.models.*;
import com.clinic.dentistry.repo.EmployeeRepository;
import com.clinic.dentistry.repo.OutpatientCardRepository;
import com.clinic.dentistry.repo.UserRepository;
import com.clinic.dentistry.service.MailService;
import com.clinic.dentistry.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    private MailService mailService;
    @Override
    public ApiResponse userRegistration(RegisterForm request) {
        if (this.isUserInDB(request)) {
            return ApiResponse.builder()
                    .status(400)
                    .message("Пользователь с такими данными уже существует!")
                    .build();
        }

        OutpatientCard outpatientCard = OutpatientCard.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .gender(request.getGender())
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
        try {
            mailService.sendNotification(
                    "Здравствуйте, " + outpatientCard.getFullName() + "! \n" +
                            "Благодарим за регистрацию на нашем сайте. С нетерпением ждем Вас в нашей стоматологической клинике!!! \n" +
                            "С уважением, \n" +
                            "Коллектив стоматологической клиники 'Улыбка премиум' ",
                    outpatientCard.getEmail(),
                    "Успешная регистрация"
            );
        } catch (MailException ignored) {
        }

        return ApiResponse.builder()
                .status(200)
                .message("Регистрация прошла успешно")
                .build();
    }

    @Override
    public ApiResponse createUser(RegisterForm request) {
        if (this.isUserInDB(request)) {
            return ApiResponse.builder()
                    .status(400)
                    .message("Пользователь с такими данными уже существует!")
                    .build();
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .roles(request.getRoles())
                .build();

        if (user.getRoles().contains(Role.DOCTOR)) {
            Employee employee = Employee.builder()
                    .fullName(request.getFullName())
                    .jobTitle(request.getJobTitle())
                    .workStart(request.getWorkStart())
                    .workEnd(request.getWorkEnd())
                    .durationApp(request.getDurationApp())
                    .build();
            employeeRepository.save(employee);
            user.setEmployee(employee);
        }

        if (user.getRoles().contains(Role.USER)) {
            OutpatientCard outpatientCard = OutpatientCard.builder()
                    .email(request.getEmail())
                    .fullName(request.getFullName())
                    .gender(request.getGender())
                    .build();
            outpatientCardRepository.save(outpatientCard);
            user.setOutpatientCard(outpatientCard);
        }

        userRepository.save(user);
        if (user.getOutpatientCard() != null){
            try {
                mailService.sendNotification(
                        "Здравствуйте, " + user.getOutpatientCard().getFullName() + "! \n" +
                                "Вы успешно зарегистрированы администратором на нашем сайте. С нетерпением ждем Вас в нашей стоматологической клинике!!! \n" +
                                "С уважением, \n" +
                                "Коллектив стоматологической клиники 'Улыбка премиум' ",
                        user.getOutpatientCard().getEmail(),
                        "Успешная регистрация"
                );
            } catch (MailException ignored) {
            }
        }

        return ApiResponse.builder()
                .status(200)
                .message("Регистрация прошла успешно")
                .build();
    }

    @Override
    public ApiResponse editUser(Long userId, UserEditForm form) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return ApiResponse.builder()
                    .status(404)
                    .message("Не найден пользователь с ID " + userId)
                    .build();
        }

        if (userRepository.findUserByUsername(form.getUsername()) != null &&
                !optionalUser.get().getUsername().equals(form.getUsername())){
            return ApiResponse.builder()
                    .status(400)
                    .message("Пользователь с такими данными уже существует!")
                    .build();
        }

        try {
            User userDb = optionalUser.get();

            Set<Role> roles = form.getRoles() != null ? form.getRoles() : Collections.emptySet();

            if (form.getUsername() != null) userDb.setUsername(form.getUsername());

            if (form.getActive() != null) userDb.setActive(form.getActive());

            if (form.getRoles() != null) userDb.setRoles(form.getRoles());

            if (form.getPassword() != null && !form.getPassword().isEmpty()) {
                userDb.setPassword(passwordEncoder.encode(form.getPassword()));
            }

            if (roles.contains(Role.USER)) {
                OutpatientCard card = userDb.getOutpatientCard() != null ? userDb.getOutpatientCard() : new OutpatientCard();
                card.setEmail(form.getEmail());
                card.setFullName(form.getFullName());
                card.setGender(form.getGender());
                outpatientCardRepository.save(card);
                userDb.setOutpatientCard(card);
            } else {
                OutpatientCard card = userDb.getOutpatientCard();
                userDb.setOutpatientCard(null);
                if (card != null) {
                    outpatientCardRepository.delete(card);
                }
            }

            if (roles.contains(Role.DOCTOR)) {
                Employee employee = employeeRepository.findEmployeeById(form.getEmployeeId());
                userDb.setEmployee(employee);
            } else {
                Employee employee = userDb.getEmployee();
                userDb.setEmployee(null);
                if (employee != null) {
                    employeeRepository.delete(employee);
                }
            }

            userRepository.save(userDb);
            return ApiResponse.builder()
                    .status(200)
                    .message("Пользователь отредактирован")
                    .build();
        }catch (Exception e){
            return ApiResponse.builder()
                    .status(500)
                    .message(e.getClass().getSimpleName() + " " + e.getMessage())
                    .build();
        }

    }

    @Override
    public boolean isUsernameVacant(String username) {
        User user = userRepository.findUserByUsername(username);
        return user == null;
    }

    private boolean isUserInDB(RegisterForm request) {
        User user = userRepository.findByUsername(request.getUsername());
        return user != null;

       /* OutpatientCard outpatientCard = outpatientCardRepository.findByEmail(request.getEmail());
        if (outpatientCard != null) {
            return true;
        }*/
    }

}
