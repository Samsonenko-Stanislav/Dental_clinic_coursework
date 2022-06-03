package com.clinic.dentistry.repo;

import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    Iterable<Appointment> findByActiveTrue();
    Iterable<Appointment> findByClientOrDoctorAndActiveTrue(User client, User doctor);
}
