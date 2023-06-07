package com.clinic.dentistry.controllers;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

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
    public HashMap<String, Object> userList(
                           @RequestParam(value = "withArchived", required = false) String withArchived
                           ) {
        HashMap<String, Object> model = new HashMap<>();
        if (withArchived != null){
            model.put("users", userService.findAllUsers());
            model.put("withArchived", true);
        } else {
            model.put("users", userService.findAllActiveUsers());
            model.put("withArchived", false);
        }
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
    public <changePassword> HashMap<String, Object> userMeEdit(@AuthenticationPrincipal User user,
     @RequestParam ("updateUser") User updateUser,
     @RequestParam(value = "changePassword", required = false) Boolean changePassword
    ){
        HashMap<String, Object> model = new HashMap<>();
        if (registrationService.isUsernameVacant(updateUser.getUsername())){
            model.put("message", "Данные успешно обновлены!");
            outpatientCardService.userMeEdit(user, updateUser, changePassword);
            model.put("user", user);
            return model;
        }
        else {
            model.put("message", "Пользователь с таким логином уже существует!");
            model.put("user", user);
            return model;
        }


    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public HashMap<String, Object> userNewForm() {
        HashMap<String, Object> model = new HashMap<>();
        model.put("roles", Role.values());
        return model;
    }

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpStatus userNewForm(
           @RequestParam("user") User user,
           @RequestParam("outpatientCard") OutpatientCard outpatientCard,
           @RequestParam("employee") Employee employee
    ) {
        HashMap<String, Object> model = new HashMap<>();
        if (registrationService.isUserInDB(user)) {
            model.put("message", "Пользователь с таким логином уже существует!");
            return HttpStatus.BAD_REQUEST;
        }
        registrationService.createUser(user, outpatientCard, employee);
        return HttpStatus.CREATED;

    }


    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpStatus userEdit(
            @RequestParam("user") User user,
            @RequestParam("employee") Employee employee,
            @RequestParam("outpatientCard") OutpatientCard outpatientCard,
            @RequestParam(value = "changePassword", required = false) Boolean changePassword
            ) {
        registrationService.editUser(user, employee, outpatientCard, changePassword);
        return HttpStatus.OK;
    }
}
