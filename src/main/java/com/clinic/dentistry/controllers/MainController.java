package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(HttpSession session,
                       @AuthenticationPrincipal User user,
                       Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }

}
