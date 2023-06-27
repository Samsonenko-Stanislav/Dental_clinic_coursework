package com.clinic.dentistry.controllers;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.models.Good;
import com.clinic.dentistry.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/good")
@PreAuthorize("hasAuthority('ADMIN')")
public class GoodController {
    @Autowired
    private GoodService goodService;

    @GetMapping
    public HashMap<String, Object> goodList() {
        HashMap<String, Object> model = new HashMap<>();
        Iterable<Good> goods;
        goods = goodService.findAllGoods();
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
    public ResponseEntity<ApiResponse> goodNewForm(
            @RequestBody Good good
    ) {
       ApiResponse response = goodService.goodSave(good);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }


    @PostMapping("{goodId}")
    public ResponseEntity<ApiResponse> goodEdit(@PathVariable("goodId") Long goodId, @RequestBody Good newData) {
        ApiResponse response =goodService.goodEdit(goodId, newData);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

}
