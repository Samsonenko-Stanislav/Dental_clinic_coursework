package com.clinic.dentistry.repo;

import com.clinic.dentistry.models.Check;
import com.clinic.dentistry.models.CheckLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckLineRepository extends JpaRepository<CheckLine, Long> {
    Iterable<CheckLine> findByCheck(Check check);

}
