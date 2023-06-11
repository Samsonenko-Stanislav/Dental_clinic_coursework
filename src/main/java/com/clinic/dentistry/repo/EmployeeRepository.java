package com.clinic.dentistry.repo;

import com.clinic.dentistry.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository  extends JpaRepository<Employee, Long> {
    Employee findEmployeeById(Long id);
}
