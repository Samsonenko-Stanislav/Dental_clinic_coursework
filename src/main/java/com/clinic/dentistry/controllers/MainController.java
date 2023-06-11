package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.Good;
import com.clinic.dentistry.models.User;
import com.clinic.dentistry.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class MainController {
    @Autowired
    private GoodService goodService;

    @GetMapping("/")
    public HashMap<String, Object> home(@AuthenticationPrincipal User user) {
        HashMap<String, Object> model = new HashMap<>();
        model.put("title", "Главная страница");
        return model;
    }
    @GetMapping("/price")
    public HashMap<String, Object> price() {
        HashMap<String, Object> model = new HashMap<>();
        Iterable<Good> goods = goodService.findActiveGoods();
        model.put("goods", goods);
        return model;
    }
}
