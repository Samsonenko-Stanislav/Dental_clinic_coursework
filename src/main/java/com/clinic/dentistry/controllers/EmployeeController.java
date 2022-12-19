package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.Employee;
import com.clinic.dentistry.repo.EmployeeRepository;
import com.clinic.dentistry.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Controller
@RequestMapping("/employee")
@PreAuthorize("hasAuthority('ADMIN')")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public String employeeList(Model model) {
        model.addAttribute("employees", employeeService.findAllEmployees());
        return "employee-list";
    }

    @GetMapping("{employeeId}")
    public String employeeEditForm(@PathVariable("employeeId") Long employeeId, Model model) {
        Employee employee = employeeService.findEmployee(employeeId);
        if (employee != null){
        model.addAttribute("employee", employee);
        return "employee-edit";
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/new")
    public String employeeNewForm(Model model) {
        return "employee-new";
    }

    @PostMapping("/new")
    public String employeeNewForm(
            @RequestParam Map<String, String> form,
            Employee employee,
            Map<String, Object> model
    ) {
        employeeService.saveEmployee(employee);
        return "redirect:/employee";
    }


    @PostMapping
    public String employeeSave(
            @RequestParam("employeeId") Employee employee,
            Employee employee_new,
            @RequestParam Map<String, String> form
    ) {
        employeeService.editEmployee(employee, employee_new);
        return "redirect:/employee";
    }

}
