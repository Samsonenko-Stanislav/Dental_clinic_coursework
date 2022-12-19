package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.models.Employee;
import com.clinic.dentistry.repo.EmployeeRepository;
import com.clinic.dentistry.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAllEmployees(){
        return employeeRepository.findAll();
    }

    @Override
    public void saveEmployee(Employee employee){
        employeeRepository.save(employee);
    }

    @Override
    public void editEmployee(Employee employee, Employee employee_new){
        employee.setFullName(employee_new.getFullName());
        employee.setJobTitle(employee_new.getJobTitle());
        employee.setWorkStart(employee_new.getWorkStart());
        employee.setWorkEnd(employee_new.getWorkEnd());
        employee.setDurationApp(employee_new.getDurationApp());
        employeeRepository.save(employee);
    }

    @Override
    public Employee findEmployee(Long id) {
        return employeeRepository.findEmployeeById(id);
    }
}
