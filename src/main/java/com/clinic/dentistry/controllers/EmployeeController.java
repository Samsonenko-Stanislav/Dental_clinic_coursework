package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.Employee;
import com.clinic.dentistry.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        if (employee != null){
        model.put("employee", employee);
        return model;
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/new")
    public HttpStatus employeeNewForm() {
        return HttpStatus.OK;
    }

    @PostMapping("/new")
    public HttpStatus employeeNewForm(
          @RequestParam("employee")  Employee employee
    ) {
        employeeService.saveEmployee(employee);
        return HttpStatus.CREATED;
    }


    @PostMapping
    public HttpStatus employeeSave(
            @RequestParam("employeeId") Employee employee,
            @RequestParam Employee employee_new
    ) {
        employeeService.editEmployee(employee, employee_new);
        return HttpStatus.OK;
    }

}
