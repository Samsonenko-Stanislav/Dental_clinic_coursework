package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.Employee;
import com.clinic.dentistry.models.Role;
import com.clinic.dentistry.models.User;
import com.clinic.dentistry.repo.EmployeeRepository;
import com.clinic.dentistry.repo.UserRepository;
import org.hibernate.collection.internal.PersistentSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user-list";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("employees", employeeRepository.findAll());
        return "user-edit";
    }

    @GetMapping("/new")
    public String userNewForm(Model model) {
        model.addAttribute("roles", Role.values());
        return "user-new";
    }

    @PostMapping("/new")
    public String userNewForm(
            @RequestParam Map<String, String> form,
            User user,
            Map<String, Object> model
    ) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "user-new";
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
    public String userSave(
            @RequestParam("userId") User user,
            @RequestParam Map<String, String> form,
            @RequestParam String username,
            Employee employee
            ) {
        user.setUsername(username);
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
