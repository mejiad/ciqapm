package com.evoltech.ciqapm.repository;

import com.evoltech.ciqapm.model.CotizaPersonal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CotizacionPersonalRepository extends JpaRepository<CotizaPersonal, Long> {
}
