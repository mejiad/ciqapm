package com.evoltech.ciqapm.repository;

import com.evoltech.ciqapm.model.Personal;
import com.evoltech.ciqapm.model.PersonalCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long> {
    List<Personal> findByCategoria(PersonalCategoria personalCategoria);
}
