package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.OutpatientCard;
import com.clinic.dentistry.models.User;
import com.clinic.dentistry.repo.UserRepository;
import com.clinic.dentistry.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/sign_up")
    public HttpStatus signUp(){
        return HttpStatus.OK;
    }

    @PostMapping("/sign_up")
    public HashMap<String, Object> signUp(@RequestParam("user") User user,
                                          @RequestParam("outpatientCard") OutpatientCard outpatientCard
                                            ){
        HashMap<String, Object> model = new HashMap<>();
        if (registrationService.isUserInDB(user)) {
            model.put("message", "Пользователь с таким логином уже существует!");
            return model;
        }

        registrationService.userRegistration(user, outpatientCard);

        return model;
    }
}
