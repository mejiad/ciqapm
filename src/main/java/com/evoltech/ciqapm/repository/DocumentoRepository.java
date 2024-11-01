package com.evoltech.ciqapm.repository;


import com.evoltech.ciqapm.model.Documento;
import com.evoltech.ciqapm.model.Proyecto;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {


    @Transactional
    List<Documento> findByProyecto(Proyecto proyecto);

}
