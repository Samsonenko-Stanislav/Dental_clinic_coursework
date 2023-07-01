package com.clinic.dentistry.repo;

import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.Check;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckRepository extends JpaRepository<Check, Long> {

    Optional<Check> findFirstByAppointment(Appointment appointment);
}
