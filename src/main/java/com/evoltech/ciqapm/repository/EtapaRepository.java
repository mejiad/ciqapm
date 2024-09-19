package com.evoltech.ciqapm.repository;

import com.evoltech.ciqapm.model.Etapa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtapaRepository extends JpaRepository<Etapa, Long> {
}
