package com.clinic.dentistry.service.impl;

import com.clinic.dentistry.dto.ApiResponse;
import com.clinic.dentistry.dto.EmployeeDto;
import com.clinic.dentistry.models.Employee;
import com.clinic.dentistry.repo.EmployeeRepository;
import com.clinic.dentistry.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
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
                .workDays(dto.getWorkDays())
                .durationApp(dto.getDurationApp())
                .build();
        employeeRepository.save(employee);

        return ApiResponse.builder()
                .status(HttpStatus.CREATED)
                .message("Сотрудник успешно создан!")
                .build();
    }

    @Override
    public ApiResponse editEmployee(Long employeeId, EmployeeDto dto){
        Employee employee = employeeRepository.findEmployeeById(employeeId);

        if (employee == null) {
            return ApiResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Не найден сотрудник с ID " + employeeId)
                    .build();
        }

       if (dto.getFullName() != null) employee.setFullName(dto.getFullName());
       if (dto.getJobTitle() != null) employee.setJobTitle(dto.getJobTitle());
       if (dto.getWorkStart() != null) employee.setWorkStart(dto.getWorkStart());
       if (dto.getWorkEnd() != null) employee.setWorkEnd(dto.getWorkEnd());
       if (dto.getDurationApp() != null) employee.setDurationApp(dto.getDurationApp());
       if (dto.getWorkDays() != null) employee.setWorkDays(dto.getWorkDays());

       employeeRepository.save(employee);

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Данные о сотруднике успешно обновлены!")
                .build();
    }

    @Override
    public Employee findEmployee(Long id) {
        return employeeRepository.findEmployeeById(id);
    }

    @Override
    public HashMap<String, Object> getEmployeeList(){
        HashMap<String, Object> model = new HashMap<>();
        model.put("employees", findAllEmployees());
        return model;
    }

    @Override
    public HashMap<String, Object> getEmpl(Long employeeId){
        HashMap<String, Object> model = new HashMap<>();
        Employee employee = findEmployee(employeeId);
        if (employee != null) {
            model.put("employee", employee);
            return model;
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND
        );
    }
}
