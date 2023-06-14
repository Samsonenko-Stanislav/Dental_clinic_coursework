package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.EmployeeDto;
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
    public ApiResponse saveEmployee(EmployeeDto dto){
        Employee employee = Employee.builder()
                .fullName(dto.getFullName())
                .jobTitle(dto.getJobTitle())
                .workStart(dto.getWorkStart())
                .workEnd(dto.getWorkEnd())
                .durationApp(dto.getDurationApp())
                .build();
        employeeRepository.save(employee);

        return ApiResponse.builder()
                .status(201)
                .message("Success")
                .build();
    }

    @Override
    public ApiResponse editEmployee(Long employeeId, EmployeeDto dto){
        Employee employee = employeeRepository.findEmployeeById(employeeId);

        if (employee == null) {
            return ApiResponse.builder()
                    .status(404)
                    .message("Не найден работник с ID " + employeeId)
                    .build();
        }

       if (dto.getFullName() != null) employee.setFullName(dto.getFullName());
       if (dto.getJobTitle() != null) employee.setJobTitle(dto.getJobTitle());
       if (dto.getWorkStart() != null) employee.setWorkStart(dto.getWorkStart());
       if (dto.getWorkEnd() != null) employee.setWorkEnd(dto.getWorkEnd());
       if (dto.getDurationApp() != null) employee.setDurationApp(dto.getDurationApp());

       employeeRepository.save(employee);

        return ApiResponse.builder()
                .status(201)
                .message("Success")
                .build();
    }

    @Override
    public Employee findEmployee(Long id) {
        return employeeRepository.findEmployeeById(id);
    }
}
