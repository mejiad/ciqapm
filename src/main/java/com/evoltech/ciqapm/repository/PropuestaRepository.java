package com.evoltech.ciqapm.repository;

import com.evoltech.ciqapm.dto.PropuestaDto;
import com.evoltech.ciqapm.dto.PropuestaDtoInterface;
import com.evoltech.ciqapm.model.Propuesta;
import com.evoltech.ciqapm.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PropuestaRepository extends JpaRepository<Propuesta, Long> {

    // List<Propuesta> findByProyecto(Proyecto proyecto);
    <T> Collection<T> findByProyecto(Proyecto proyecto, Class<T> type);

    // List<PropuestaDto> findByProyecto(Proyecto proyecto, PropuestaDtoInterface.class);
}
