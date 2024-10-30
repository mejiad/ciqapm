package com.evoltech.ciqapm.repository;

import com.evoltech.ciqapm.model.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    Page<Servicio> findByDescripcionContains(String descripcion, Pageable page);
}
