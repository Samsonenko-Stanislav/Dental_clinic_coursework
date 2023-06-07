package com.clinic.dentistry.service.impl;

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
    public List<OutpatientCard> findAllCards(){
        return outpatientCardRepository.findAll();
    }

    @Override
    public void userMeEdit(User user, User updateUser, Boolean changePassword){
        user.getOutpatientCard().setFullName(updateUser.getFullName());
        user.getOutpatientCard().setEmail(updateUser.getOutpatientCard().getEmail());
        user.setUsername(updateUser.getUsername());
        if (changePassword){
            user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        }
        user.getOutpatientCard().setGender(updateUser.getOutpatientCard().getGender());
        outpatientCardRepository.save(updateUser.getOutpatientCard());
        usesRepositotory.save(user);
    }
}
