package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.OutpatientCard;
import com.clinic.dentistry.models.Role;
import com.clinic.dentistry.models.User;
import com.clinic.dentistry.repo.OutpatientCardRepository;
import com.clinic.dentistry.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OutpatientCardRepository outpatientCardRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/sign_up")
    public String signUp(){
        return "sign-up";
    }

    @PostMapping("/sign_up")
    public String signUp(User user, OutpatientCard outpatientCard, Map<String, Object> model){
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            model.put("message", "Пользователь с таким логином уже существует!");
            return "sign-up";
        }

        outpatientCardRepository.save(outpatientCard);

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setOutpatientCard(outpatientCard);
        userRepository.save(user);

        return "redirect:/login";
    }
}
