package com.evoltech.ciqapm.repository;

import com.evoltech.ciqapm.model.Etapa;
import com.evoltech.ciqapm.model.EtapaServicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EtapaServicioRepository extends JpaRepository<EtapaServicio, Long> {

    List<EtapaServicio> findByEtapa(Etapa etapa);

}