package com.clinic.dentistry.repo;

import com.clinic.dentistry.models.Appointment;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
}
