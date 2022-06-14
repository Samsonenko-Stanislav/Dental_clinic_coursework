package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.Good;
import com.clinic.dentistry.repo.GoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/check")
public class CheckController {

    @Autowired
    private GoodRepository goodRepository;

    @GetMapping
    public String employeeList(Model model) {
        model.addAttribute("goods", goodRepository.findAll());
        return "check-list";
    }

    @GetMapping("{good}")
    public String employeeEditForm(@PathVariable Good good, Model model) {
        model.addAttribute("good", good);
        return "check-edit";
    }

    @GetMapping("/new")
    public String employeeNewForm(Model model) {
        return "good-new";
    }

    @PostMapping("/new")
    public String employeeNewForm(
            @RequestParam Map<String, String> form,
            Good good,
            Map<String, Object> model
    ) {
        good.set_active(true);
        goodRepository.save(good);
        return "redirect:/good";
    }


    @PostMapping
    public String employeeSave(
            @RequestParam("checkId") Good good,
            Good good_new,
            @RequestParam Map<String, String> form
    ) {
        good.setName(good_new.getName());
        good.setPrice(good_new.getPrice());
        goodRepository.save(good);
        return "redirect:/good";
    }

}
