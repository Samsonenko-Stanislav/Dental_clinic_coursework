package com.clinic.dentistry.service;

import com.clinic.dentistry.models.Employee;

import java.util.List;

public interface EmployeeService {
        List<Employee> findAllEmployees();
        void saveEmployee(Employee employee);
        void editEmployee(Employee employee, Employee employee_new);

}
