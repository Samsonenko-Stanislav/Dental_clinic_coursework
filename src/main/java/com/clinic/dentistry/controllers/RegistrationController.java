package com.clinic.dentistry.controllers;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.auth.RegisterRequest;
import com.clinic.dentistry.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/sign_up")
    public ResponseEntity<ApiResponse> signUp(@Valid @RequestBody RegisterRequest request){
        ApiResponse response = registrationService.userRegistration(request);
        return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatus()));
    }
}
