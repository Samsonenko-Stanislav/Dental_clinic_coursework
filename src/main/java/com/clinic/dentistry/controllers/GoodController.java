package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.Good;
import com.clinic.dentistry.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/good")
@PreAuthorize("hasAuthority('ADMIN')")
public class GoodController {
    @Autowired
    private GoodService goodService;

    @GetMapping
    public HashMap<String, Object> goodList(
            @RequestParam(value = "withArchived", required = false) String withArchived) {
        HashMap<String, Object> model = new HashMap<>();
        Iterable<Good> goods;
        if (withArchived != null){
            goods = goodService.findAllGoods();
            model.put("withArchived", true);
        } else {
            goods = goodService.findActiveGoods();
            model.put("withArchived", false);
        }
        model.put("goods", goods);
        return model;
    }

    @GetMapping("{goodId}")
    public HashMap<String, Object> goodEditForm(@PathVariable("goodId") Long goodId) {
        HashMap<String, Object> model = new HashMap<>();
        Good good = goodService.findGood(goodId);
        if (good != null) {
            model.put("good", good);
            return model;
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/new")
    public HttpStatus goodNewForm() {
        return HttpStatus.OK;
    }

    @PostMapping("/new")
    public HttpStatus goodNewForm(
            @RequestParam("good") Good good
    ) {
        goodService.goodSave(good);
        return HttpStatus.CREATED;
    }


    @PostMapping
    public HttpStatus goodEdit(
            @RequestParam("good") Good good,
            @RequestParam("good_new") Good good_new
    ) {
        goodService.goodEdit(good, good_new);
        return HttpStatus.OK;
    }

}
