package com.evoltech.ciqapm.repository;

import com.evoltech.ciqapm.model.jpa.Convocatoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConvocatoriaRepository extends JpaRepository<Convocatoria, Long> {

}
