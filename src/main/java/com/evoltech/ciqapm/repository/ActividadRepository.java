package com.evoltech.ciqapm.repository;

import com.evoltech.ciqapm.model.Actividad;
import com.evoltech.ciqapm.model.Etapa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {

    List<Actividad> findByEtapa(Etapa etapa);
}
