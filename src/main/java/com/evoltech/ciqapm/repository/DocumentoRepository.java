package com.evoltech.ciqapm.repository;


import com.evoltech.ciqapm.model.Documento;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {


}
