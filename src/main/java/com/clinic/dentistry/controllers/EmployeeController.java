package com.clinic.dentistry.controllers;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.EmployeeDto;
import com.clinic.dentistry.models.Employee;
import com.clinic.dentistry.service.EmployeeService;
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
@RequestMapping("/employee")
@PreAuthorize("hasAuthority('ADMIN')")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public HashMap<String, Object> employeeList() {
        HashMap<String, Object> model = new HashMap<>();
        model.put("employees", employeeService.findAllEmployees());
        return model;
    }

    @GetMapping("{employeeId}")
    public HashMap<String, Object> employeeEditForm(@PathVariable("employeeId") Long employeeId) {
        HashMap<String, Object> model = new HashMap<>();
        Employee employee = employeeService.findEmployee(employeeId);
        if (employee != null) {
            model.put("employee", employee);
            return model;
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/new")
    public ResponseEntity<?> employeeNewForm(@RequestBody EmployeeDto employeeDto) {
        ApiResponse response = employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PostMapping("/edit/{employeeId}")
    public ResponseEntity<?> employeeSave(@PathVariable("employeeId") Long employeeId, @RequestBody EmployeeDto employeeDto) {
        if (employeeService.findEmployee(employeeId)!=null){
            ApiResponse response = employeeService.editEmployee(employeeId, employeeDto);
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND
        );
    }

}
