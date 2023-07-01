package com.clinic.dentistry.controllers;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.models.Good;
import com.clinic.dentistry.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/good")
public class GoodController {
    @Autowired
    private GoodService goodService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR')")
    @GetMapping
    public HashMap<String, Object> goodList() {
        return goodService.getGoodList();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{goodId}")
    public HashMap<String, Object> goodEditForm(@PathVariable("goodId") Long goodId) {
        return goodService.goodGet(goodId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/new")
    public ResponseEntity<ApiResponse> goodNewForm(
            @RequestBody Good good
    ) {
       ApiResponse response = goodService.goodSave(good);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{goodId}")
    public ResponseEntity<ApiResponse> goodEdit(@PathVariable("goodId") Long goodId, @RequestBody Good newData) {
        ApiResponse response =goodService.goodEdit(goodId, newData);
        return new ResponseEntity<>(response, response.getStatus());
    }

}
