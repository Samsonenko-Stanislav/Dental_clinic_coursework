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
    public void userMeEdit(User user, Map<String, String> form){
        user.getOutpatientCard().setFullName(form.get("fullName"));
        user.getOutpatientCard().setEmail(form.get("email"));
        user.setUsername(form.get("username"));
        if (form.get("changePassword").equals("on")){
            user.setPassword(passwordEncoder.encode(form.get("password")));
        }
        //TODO Понять почему не меняется пол, а точнее почему не находит MALE и FEMALE
        if (form.get("MALE") != null){
            user.getOutpatientCard().setGender(Gender.MALE);
        }
        if (form.get("FEMALE") != null) {
            user.getOutpatientCard().setGender(Gender.FEMALE);
        }
        outpatientCardRepository.save(user.getOutpatientCard());
        usesRepositotory.save(user);
    }
}
