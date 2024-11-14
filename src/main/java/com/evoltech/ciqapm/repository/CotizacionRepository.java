package com.evoltech.ciqapm.repository;

import com.evoltech.ciqapm.model.Cotizacion;
import com.evoltech.ciqapm.model.Industria;
import com.evoltech.ciqapm.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {

    List<Cotizacion> findByIndustria(Industria industria);
}
