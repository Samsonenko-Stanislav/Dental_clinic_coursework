package com.clinic.dentistry.service;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.EmployeeDto;
import com.clinic.dentistry.models.Employee;

import java.util.HashMap;
import java.util.List;

public interface EmployeeService {
        List<Employee> findAllEmployees();
        ApiResponse saveEmployee(EmployeeDto employeeDto);
        ApiResponse editEmployee(Long employeeId, EmployeeDto employeeDto);
        Employee findEmployee(Long id);

        HashMap<String, Object> getEmployeeList();

        HashMap<String, Object> getEmpl(Long employeeId);
}
