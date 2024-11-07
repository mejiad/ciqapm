package com.evoltech.ciqapm.repository;

import com.evoltech.ciqapm.model.CotizaServicio;
import com.evoltech.ciqapm.model.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CotizacionServiciosRepository extends JpaRepository<CotizaServicio, Long> {

    List<CotizaServicio> findByCotizacion(Cotizacion cotizacion);
}
