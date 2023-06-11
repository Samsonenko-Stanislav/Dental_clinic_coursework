package com.clinic.dentistry.repo;

import com.clinic.dentistry.models.Good;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodRepository extends JpaRepository<Good, Long> {
    Iterable<Good> findAllByActiveTrue();
    Good findGoodById(Long id);
}
