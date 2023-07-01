package com.clinic.dentistry.controllers;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.user.RegisterForm;
import com.clinic.dentistry.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/sign_up")
    public ResponseEntity<ApiResponse> signUp(@Valid @RequestBody RegisterForm request){
        ApiResponse response = registrationService.userRegistration(request);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
