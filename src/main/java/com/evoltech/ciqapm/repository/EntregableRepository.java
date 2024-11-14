package com.evoltech.ciqapm.repository;

import com.evoltech.ciqapm.model.Entregable;
import com.evoltech.ciqapm.model.Etapa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntregableRepository extends JpaRepository<Entregable, Long> {

    List<Entregable> findByEtapa(Etapa etapa);
}
