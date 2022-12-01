package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.Good;
import com.clinic.dentistry.service.GoodService;
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
    private GoodService goodService;

    @GetMapping
    public String goodList(
            Model model,
            @RequestParam(value = "withArchived", required = false) String withArchived) {
        Iterable<Good> goods;
        if (withArchived != null){
            goods = goodService.findAllGoods();
            model.addAttribute("withArchived", true);
        } else {
            goods = goodService.findActiveGoods();
            model.addAttribute("withArchived", false);
        }
        model.addAttribute("goods", goods);
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
        goodService.goodSave(good);
        return "redirect:/good";
    }


    @PostMapping
    public String goodEdit(
            @RequestParam("goodId") Good good,
            Good good_new,
            @RequestParam Map<String, String> form
    ) {
        goodService.goodEdit(good, good_new, form);
        return "redirect:/good";
    }

}
