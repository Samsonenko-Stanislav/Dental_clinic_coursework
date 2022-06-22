package com.clinic.dentistry.controllers;

import com.clinic.dentistry.models.Employee;
import com.clinic.dentistry.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/employee")
@PreAuthorize("hasAuthority('ADMIN')")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public String employeeList(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "employee-list";
    }

    @GetMapping("{employee}")
    public String employeeEditForm(@PathVariable Employee employee, Model model) {
        model.addAttribute("employee", employee);
        return "employee-edit";
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
        employeeRepository.save(employee);
        return "redirect:/employee";
    }


    @PostMapping
    public String employeeSave(
            @RequestParam("employeeId") Employee employee,
            Employee employee_new,
            @RequestParam Map<String, String> form
    ) {
        employee.setFullName(employee_new.getFullName());
        employee.setJobTitle(employee_new.getJobTitle());
        employee.setWorkStart(employee_new.getWorkStart());
        employee.setWorkEnd(employee_new.getWorkEnd());
        employeeRepository.save(employee);
        return "redirect:/employee";
    }

}
