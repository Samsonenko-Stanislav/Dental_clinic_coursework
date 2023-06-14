package com.clinic.dentistry.service;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.EmployeeDto;
import com.clinic.dentistry.models.Employee;

import java.util.List;

public interface EmployeeService {
        List<Employee> findAllEmployees();
        ApiResponse saveEmployee(EmployeeDto employeeDto);
        void editEmployee(Employee employee, Employee employee_new);
        Employee findEmployee(Long id);

}
