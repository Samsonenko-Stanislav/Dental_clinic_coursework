package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.Employee;
import com.clinic.dentistry.models.OutpatientCard;
import com.clinic.dentistry.models.Role;
import com.clinic.dentistry.models.User;
import com.clinic.dentistry.service.EmployeeService;
import com.clinic.dentistry.service.OutpatientCardService;
import com.clinic.dentistry.service.RegistrationService;
import com.clinic.dentistry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
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
    public String userList(Model model,
                           @RequestParam(value = "withArchived", required = false) String withArchived
                           ) {
        if (withArchived != null){
            model.addAttribute("users", userService.findAllUsers());
            model.addAttribute("withArchived", true);
        } else {
            model.addAttribute("users", userService.findAllActiveUsers());
            model.addAttribute("withArchived", false);
        }
        return "user-list";
    }

    @GetMapping("{user}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("employees", employeeService.findAllEmployees());
        model.addAttribute("users", outpatientCardService.findAllCards());
        if(user.getRoles().contains(Role.USER))
            return "user-edit";
        return "user-edit-2";

    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('USER')")
    public String userMeEditForm(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "user-me";
    }

    @PostMapping("/me")
    @PreAuthorize("hasAuthority('USER')")
    public String userMeEdit(@AuthenticationPrincipal User user,
                             @RequestParam Map<String, String> form,
                             Model model) {
        outpatientCardService.userMeEdit(user, form);
        model.addAttribute("user", user);
        return "home";
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
        if (registrationService.isUserInDB(user)) {
            model.put("message", "Пользователь с таким логином уже существует!");
            return "user-new";
        }
        registrationService.createUser(form, user, outpatientCard, employee);
        return "redirect:/user";
    }


    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userEdit(
            @RequestParam("userId") User user,
            @RequestParam Map<String, String> form,
            @RequestParam String username,
            @RequestParam(value = "active", required = false) String active,
            Employee employee,
            OutpatientCard outpatientCard
            ) {
        Boolean flag;
        if (user.getRoles().contains(Role.USER)) {
            flag = true;
        }
        else flag = false;
        registrationService.editUser(user, username, active, employee, outpatientCard, form);
        if (flag && user.getRoles().contains(Role.USER)) {
            return "redirect:/user";
        } else if (!flag && user.getRoles().contains(Role.USER)) {
            return "redirect:/user/" + user.getId().toString();

        }
        return "redirect:/user";
    }
}
