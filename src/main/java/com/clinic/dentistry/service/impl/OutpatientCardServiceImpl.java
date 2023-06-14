package com.clinic.dentistry.service.impl;

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

import java.util.List;
import java.util.Map;

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
    public void userMeEdit(User user, UserEditForm form) {
        if (form.getFullName() != null) user.getOutpatientCard().setFullName(form.getFullName());

        if (form.getEmail() != null) user.getOutpatientCard().setEmail(form.getEmail());

        if (form.getUsername() != null) user.setUsername(form.getUsername());

        if (form.getPassword() != null) user.setPassword(passwordEncoder.encode(form.getPassword()));

        if (form.getGender() != null) user.getOutpatientCard().setGender(form.getGender());

        outpatientCardRepository.save(user.getOutpatientCard());
        usesRepositotory.save(user);
    }
}
