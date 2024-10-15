package com.evoltech.ciqapm.repository.datos;

import com.evoltech.ciqapm.model.datos.DatosConahcyt;
import com.evoltech.ciqapm.model.datos.DatosInternos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternosRepository extends JpaRepository<DatosInternos, Long> {

}
