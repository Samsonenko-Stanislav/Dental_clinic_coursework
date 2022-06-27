package com.clinic.dentistry.repo;

import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.Employee;
import com.clinic.dentistry.models.OutpatientCard;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Iterable<Appointment> findByActiveTrue();
    Iterable<Appointment> findByClient(OutpatientCard outpatientCard);
    Iterable<Appointment> findByClientAndActiveTrue(OutpatientCard outpatientCard);
    Iterable<Appointment> findByDoctorAndActiveTrue(Employee employee);
}
