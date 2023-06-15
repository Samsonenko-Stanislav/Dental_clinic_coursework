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
        ApiResponse response = new ApiResponse();
        OutpatientCard card = user.getOutpatientCard();
        if (card == null) {
            response.setStatus(500);
            response.setMessage("Ошибка структуры данных");
            return response;
        }

        StringBuilder sb = new StringBuilder();

        if (form.getUsername() != null && !form.getUsername().equals(user.getUsername())) {
            if (usesRepositotory.findByUsername(form.getUsername()) != null) {
                user.setUsername(form.getUsername());
                sb.append("Логин изменен\n");
            } else {
                response.setStatus(400);
                response.setMessage("Пользователь с таким логином уже существует!");
                return response;
            }
        }

        if (form.getPassword() != null && !form.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(form.getPassword()));
            sb.append("Пароль изменен\n");
        }

        if (form.getFullName() != null && !form.getFullName().equals(card.getFullName())) {
            card.setFullName(form.getFullName());
            if (user.getEmployee() != null){
                user.getEmployee().setFullName(form.getFullName());
            }
            sb.append("Имя изменено\n");
        }

        if (form.getEmail() != null && !form.getEmail().equals(card.getEmail())) {
            OutpatientCard otherCard = outpatientCardRepository.findByEmail(form.getEmail());
            if (otherCard != null) {
                response.setStatus(400);
                response.setMessage("Пользователь с таким email уже существует!");
                return response;
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

        response.setStatus(200);
        response.setMessage(sb.toString());
        return response;
    }

}
