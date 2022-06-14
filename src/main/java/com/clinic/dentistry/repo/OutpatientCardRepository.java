package com.clinic.dentistry.repo;

import com.clinic.dentistry.models.Appointment;
import com.clinic.dentistry.models.Check;
import com.clinic.dentistry.models.OutpatientCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutpatientCardRepository extends JpaRepository<OutpatientCard, Long> {
}
