package com.clinic.dentistry.controllers;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.user.RegisterForm;
import com.clinic.dentistry.dto.user.UserEditForm;
import com.clinic.dentistry.models.User;
import com.clinic.dentistry.service.OutpatientCardService;
import com.clinic.dentistry.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    @Autowired
    private OutpatientCardService outpatientCardService;
    @Autowired
    private RegistrationService registrationService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public HashMap<String, Object> userList() {
        return registrationService.getUserList();
    }

    @GetMapping("{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public HashMap<String, Object> userEditForm(@PathVariable("userId") Long userId) {
        return registrationService.getUsr(userId);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('USER')")
    public HashMap<String, Object> userMeEditForm(@AuthenticationPrincipal User user) {
        return registrationService.getMyProfile(user);
    }

    @PutMapping("/me")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> userMeEdit(@AuthenticationPrincipal User user, @RequestBody UserEditForm editForm) {
        ApiResponse response = outpatientCardService.userMeEdit(user, editForm);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> userNewForm(@RequestBody RegisterForm request) {
        ApiResponse response = registrationService.createUser(request);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/edit/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> userEdit(@PathVariable("userId") Long userId, @RequestBody UserEditForm editForm) {
        ApiResponse response = registrationService.editUser(userId, editForm);
        return new ResponseEntity<>(response, response.getStatus());
    }

}
