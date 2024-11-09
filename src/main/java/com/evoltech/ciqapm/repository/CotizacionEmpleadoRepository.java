package com.evoltech.ciqapm.repository;

import com.evoltech.ciqapm.model.CotizaEmpleado;
import com.evoltech.ciqapm.model.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CotizacionEmpleadoRepository extends JpaRepository<CotizaEmpleado, Long> {
    List<CotizaEmpleado> findByCotizacion(Cotizacion cotizacion);
}
