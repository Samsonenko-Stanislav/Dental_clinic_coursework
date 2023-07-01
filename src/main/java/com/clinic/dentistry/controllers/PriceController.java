package com.clinic.dentistry.controllers;

import com.clinic.dentistry.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/price")
public class PriceController {
    @Autowired
    private GoodService goodService;

    @GetMapping
    public HashMap<String, Object> priceList() {
        return goodService.priceGet();
    }
}
