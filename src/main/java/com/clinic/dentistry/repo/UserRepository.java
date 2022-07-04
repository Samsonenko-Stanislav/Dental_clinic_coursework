package com.clinic.dentistry.repo;

import com.clinic.dentistry.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List findByRolesInAndActiveTrue(Set roles);

    List findByActiveTrue();
}
