package com.clinic.dentistry.controllers;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.EmployeeDto;
import com.clinic.dentistry.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
@PreAuthorize("hasAuthority('ADMIN')")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public HashMap<String, Object> employeeList() {
        return employeeService.getEmployeeList();
    }

    @GetMapping("{employeeId}")
    public HashMap<String, Object> employeeEditForm(@PathVariable("employeeId") Long employeeId) {
        return employeeService.getEmpl(employeeId);
    }

    @PostMapping("/new")
    public ResponseEntity<?> employeeNewForm(@RequestBody EmployeeDto employeeDto) {
        ApiResponse response = employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/edit/{employeeId}")
    public ResponseEntity<?> employeeSave(@PathVariable("employeeId") Long employeeId, @RequestBody EmployeeDto employeeDto) {
            ApiResponse response = employeeService.editEmployee(employeeId, employeeDto);
            return new ResponseEntity<>(response, response.getStatus());
    }

}
