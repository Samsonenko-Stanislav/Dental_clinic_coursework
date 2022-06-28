package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.Employee;
import com.clinic.dentistry.models.OutpatientCard;
import com.clinic.dentistry.models.Role;
import com.clinic.dentistry.models.User;
import com.clinic.dentistry.repo.EmployeeRepository;
import com.clinic.dentistry.repo.OutpatientCardRepository;
import com.clinic.dentistry.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private OutpatientCardRepository outpatientCardRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userList(Model model,
                           @RequestParam(value = "withArchived", required = false) String withArchived
                           ) {
        if (withArchived != null){
            model.addAttribute("users", userRepository.findAll());
            model.addAttribute("withArchived", true);
        } else {
            model.addAttribute("users", userRepository.findByActiveTrue());
            model.addAttribute("withArchived", false);
        }
        return "user-list";
    }

    @GetMapping("{user}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("users", outpatientCardRepository.findAll());
        return "user-edit";
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('USER')")
    public String userMeEditForm(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "user-me";
    }

    @PostMapping("/me")
    @PreAuthorize("hasAuthority('USER')")
    public String userMeSave(@AuthenticationPrincipal User user,
                             @RequestParam String fullName,
                             Model model) {
        user.setFullName(fullName);
        outpatientCardRepository.save(user.getOutpatientCard());
        model.addAttribute("user", user);
        return "user-me";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userNewForm(Model model) {
        model.addAttribute("roles", Role.values());
        return "user-new";
    }

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userNewForm(
            @RequestParam Map<String, String> form,
            User user,
            OutpatientCard outpatientCard,
            Employee employee,
            Map<String, Object> model
    ) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            model.put("message", "Пользователь с таким логином уже существует!");
            return "user-new";
        }

        if (form.get("USER") != null && form.get("USER").equals("on")){
            outpatientCardRepository.save(outpatientCard);
            user.setOutpatientCard(outpatientCard);
        }

        if (form.get("DOCTOR") != null && form.get("DOCTOR").equals("on")){
            employeeRepository.save(employee);
            user.setEmployee(employee);
        }

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        Set<Role> user_roles = new HashSet<>();
        for (String key : form.keySet()) {
            if (roles.contains(key)){
                user_roles.add(Role.valueOf(key));
            }
        }
        user.setRoles(user_roles);

        userRepository.save(user);
        return "redirect:/user";
    }


    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userSave(
            @RequestParam("userId") User user,
            @RequestParam Map<String, String> form,
            @RequestParam String username,
            @RequestParam(value = "active", required = false) String active,
            Employee employee
            ) {
        user.setUsername(username);
        if (active != null){
            user.setActive(true);
        } else {
            user.setActive(false);
        }


        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }

        if (employee != null && employee.getId() != null){
            user.setEmployee(employee);
        } else {
            user.setEmployee(null);
        }
        userRepository.save(user);
        return "redirect:/user";
    }
}
