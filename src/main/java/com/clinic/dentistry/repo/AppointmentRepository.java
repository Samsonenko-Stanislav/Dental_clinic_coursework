package com.clinic.dentistry.repo;

import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.Employee;
import com.clinic.dentistry.models.OutpatientCard;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Iterable<Appointment> findByActiveTrue();
    Iterable<Appointment> findByClientAndConclusionNotNull(OutpatientCard outpatientCard);
    Iterable<Appointment> findByClientAndActiveTrueAndConclusionNull(OutpatientCard outpatientCard);
    Iterable<Appointment> findByDoctorAndConclusionNotNull(Employee employee);
    Iterable<Appointment> findByDoctorAndActiveTrueAndConclusionNull(Employee employee);
}
