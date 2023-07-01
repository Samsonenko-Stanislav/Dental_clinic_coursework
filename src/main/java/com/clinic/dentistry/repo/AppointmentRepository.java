package com.clinic.dentistry.repo;

import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.Employee;
import com.clinic.dentistry.models.OutpatientCard;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Iterable<Appointment> findByActiveTrueOrderByDate();
    Iterable<Appointment> findByClientAndConclusionNotNullOrderByDate(OutpatientCard outpatientCard);
    Iterable<Appointment> findByClientAndActiveTrueAndConclusionNullOrderByDate(OutpatientCard outpatientCard);
    Iterable<Appointment> findByDoctorAndConclusionNotNullOrderByDate(Employee employee);
    Iterable<Appointment> findByDoctorAndActiveTrueAndConclusionNullOrderByDate(Employee employee);
    Appointment findAppointmentById(Long id);
    Iterable<Appointment> findAppointmentByDoctorOrderByDate(Employee employee);
 }
