package com.evoltech.ciqapm.repository;

import com.evoltech.ciqapm.model.Pago;
import com.evoltech.ciqapm.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByProyecto(Proyecto proyecto);
}
