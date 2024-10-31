package com.evoltech.ciqapm.repository;

import com.evoltech.ciqapm.model.CotizacionServicios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CotizacionServiciosRepository extends JpaRepository<CotizacionServicios, Long> {
}
