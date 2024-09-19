package com.evoltech.ciqapm.repository;

import com.evoltech.ciqapm.model.Personal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long> {
}
