package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.auth.LoginResponse;
import com.clinic.dentistry.dto.user.UserEditForm;
import com.clinic.dentistry.models.Gender;
import com.clinic.dentistry.models.OutpatientCard;
import com.clinic.dentistry.models.User;
import com.clinic.dentistry.repo.OutpatientCardRepository;
import com.clinic.dentistry.repo.UserRepository;
import com.clinic.dentistry.service.OutpatientCardService;
import com.fasterxml.jackson.core.Base64Variant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OutpatientCardServiceImpl implements OutpatientCardService {
    @Autowired
    private OutpatientCardRepository outpatientCardRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository usesRepositotory;

    @Override
    public List<OutpatientCard> findAllCards() {
        return outpatientCardRepository.findAll();
    }

    @Override
    public ApiResponse userMeEdit(User user, UserEditForm form) {
        OutpatientCard card = user.getOutpatientCard();
        if (card == null) {
            return ApiResponse.builder()
                    .status(500)
                    .message("Ошибка структуры данных")
                    .build();
        }

        StringBuilder sb = new StringBuilder();

        if (form.getUsername() != null && !form.getUsername().equals(user.getUsername())) {
            if (usesRepositotory.findByUsername(form.getUsername()) != null) {
                user.setUsername(form.getUsername());
                sb.append("Логин изменен\n");
            } else {
                sb.append("Пользователь с таким логином уже существует!\n");
            }
        }

        if (form.getPassword() != null && !form.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(form.getPassword()));
            sb.append("Пароль изменен\n");
        }

        if (form.getFullName() != null && !form.getFullName().equals(card.getFullName())) {
            card.setFullName(form.getFullName());
            sb.append("Имя изменено\n");
        }

        if (form.getEmail() != null && !form.getEmail().equals(card.getEmail())) {
            OutpatientCard otherCard = outpatientCardRepository.findByEmail(form.getEmail());
            if (otherCard != null) {
                sb.append("Пользователь с таким email уже существует!\n");
            } else {
                card.setEmail(form.getEmail());
                sb.append("Email изменен\n");
            }
        }

        if (form.getGender() != card.getGender()) {
            card.setGender(form.getGender());
            sb.append("Пол изменен\n");
        }

        outpatientCardRepository.save(user.getOutpatientCard());
        usesRepositotory.save(user);

        LoginResponse response = new LoginResponse(
                this.encodeCredentials(user.getUsername(), form.getPassword()),
                user.getRoles()
        );

        return ApiResponse.builder()
                .status(200)
                .message(sb.toString())
                .data(response)
                .build();
    }

    private String encodeCredentials(String username, String password) {
        String credentials = username + ":" + password;
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }

}
