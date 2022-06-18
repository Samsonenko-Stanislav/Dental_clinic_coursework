package com.clinic.dentistry.repo;

import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.Check;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckRepository extends JpaRepository<Check, Long> {
    Iterable<Check> findByAppointment(Appointment appointment);

}
