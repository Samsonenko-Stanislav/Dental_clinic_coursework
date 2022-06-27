package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.Good;
import com.clinic.dentistry.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.clinic.dentistry.repo.GoodRepository;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {
    @Autowired
    GoodRepository goodRepository;
    @GetMapping("/")
    public String home(HttpSession session,
                       @AuthenticationPrincipal User user,
                       Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }
    @GetMapping("/price")
    public String price( Model model) {
        Iterable<Good> goods;
        goods = goodRepository.findAllByActiveTrue();
        model.addAttribute("goods", goods);
        return "price-list";
    }
}
