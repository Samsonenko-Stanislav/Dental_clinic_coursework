package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.Good;
import com.clinic.dentistry.repo.GoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/good")
@PreAuthorize("hasAuthority('ADMIN')")
public class GoodController {

    @Autowired
    private GoodRepository goodRepository;

    @GetMapping
    public String goodList(Model model) {
        model.addAttribute("goods", goodRepository.findAll());
        return "good-list";
    }

    @GetMapping("{good}")
    public String goodEditForm(@PathVariable Good good, Model model) {
        model.addAttribute("good", good);
        return "good-edit";
    }

    @GetMapping("/new")
    public String goodNewForm(Model model) {
        return "good-new";
    }

    @PostMapping("/new")
    public String goodNewForm(
            @RequestParam Map<String, String> form,
            Good good,
            Map<String, Object> model
    ) {
        good.setActive(true);
        goodRepository.save(good);
        return "redirect:/good";
    }


    @PostMapping
    public String goodSave(
            @RequestParam("goodId") Good good,
            Good good_new,
            @RequestParam Map<String, String> form
    ) {
        good.setName(good_new.getName());
        good.setPrice(good_new.getPrice());
        if (form.get("active") != null && form.get("active").equals("on")){
            good.setActive(true);
        } else {
            good.setActive(false);
        }
        goodRepository.save(good);
        return "redirect:/good";
    }

}
