package com.evoltech.ciqapm.repository.datos;

import com.evoltech.ciqapm.model.Conahcyt;
import com.evoltech.ciqapm.model.datos.DatosConahcyt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConahcytRepository extends JpaRepository<Conahcyt, Long> {

    DatosConahcyt findByNombre(String nombre);

}
