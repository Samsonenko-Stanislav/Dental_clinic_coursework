package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.Good;
import com.clinic.dentistry.models.User;
import com.clinic.dentistry.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {
    @Autowired
    private GoodService goodService;

    @GetMapping("/")
    public String home(HttpSession session,
                       @AuthenticationPrincipal User user,
                       Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }
    @GetMapping("/price")
    public String price( Model model) {
        Iterable<Good> goods = goodService.findActiveGoods();
        model.addAttribute("goods", goods);
        return "price-list";
    }
}
