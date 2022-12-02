package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.OutpatientCard;
import com.clinic.dentistry.models.User;
import com.clinic.dentistry.repo.UserRepository;
import com.clinic.dentistry.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/sign_up")
    public String signUp(){
        return "sign-up";
    }

    @PostMapping("/sign_up")
    public String signUp(User user, OutpatientCard outpatientCard, Map<String, Object> model){
        if (registrationService.isUserInDB(user)) {
            model.put("message", "Пользователь с таким логином уже существует!");
            return "sign-up";
        }

        registrationService.userRegistration(user, outpatientCard);

        return "redirect:/login";
    }
}
