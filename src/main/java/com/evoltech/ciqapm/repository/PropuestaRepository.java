package com.evoltech.ciqapm.repository;

import com.evoltech.ciqapm.model.Propuesta;
import com.evoltech.ciqapm.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropuestaRepository extends JpaRepository<Propuesta, Long> {

    List<Propuesta> findByProyecto(Proyecto proyecto);
}
