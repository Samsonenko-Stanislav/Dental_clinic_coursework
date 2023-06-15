package com.clinic.dentistry.controllers;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.user.RegisterForm;
import com.clinic.dentistry.dto.user.UserEditForm;
import com.clinic.dentistry.models.Employee;
import com.clinic.dentistry.models.OutpatientCard;
import com.clinic.dentistry.models.Role;
import com.clinic.dentistry.models.User;
import com.clinic.dentistry.service.EmployeeService;
import com.clinic.dentistry.service.OutpatientCardService;
import com.clinic.dentistry.service.RegistrationService;
import com.clinic.dentistry.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private OutpatientCardService outpatientCardService;
    @Autowired
    private RegistrationService registrationService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public HashMap<String, Object> userList() {
        HashMap<String, Object> model = new HashMap<>();
        model.put("users", userService.findAllUsers());
        model.put("withArchived", true);
        return model;
    }

    @GetMapping("{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public HashMap<String, Object> userEditForm(@PathVariable("userId") Long userId) {
        HashMap<String, Object> model = new HashMap<>();
        User user = userService.findUser(userId);
        if (user != null) {
            model.put("user", user);
            model.put("roles", Role.values());
            model.put("employees", employeeService.findAllEmployees());
            model.put("users", outpatientCardService.findAllCards());
            return model;
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('USER')")
    public HashMap<String, Object> userMeEditForm(@AuthenticationPrincipal User user) {
        HashMap<String, Object> model = new HashMap<>();
        model.put("user", user);
        return model;
    }

    @PostMapping("/me")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> userMeEdit(@AuthenticationPrincipal User user, @RequestBody UserEditForm editForm) {
        ApiResponse response = outpatientCardService.userMeEdit(user, editForm);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> userNewForm(@RequestBody RegisterForm request) {
        ApiResponse response = registrationService.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PostMapping("/edit/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> userEdit(@PathVariable("userId") Long userId, @RequestBody UserEditForm editForm) {
        ApiResponse response = registrationService.editUser(userId, editForm);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

}
