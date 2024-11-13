package com.evoltech.ciqapm.repository.datos;

import com.evoltech.ciqapm.model.Industria;
import com.evoltech.ciqapm.model.Proyecto;
import com.evoltech.ciqapm.model.datos.DatosConahcyt;
import com.evoltech.ciqapm.model.datos.DatosIndustria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndustriaRepository extends JpaRepository<Industria, Long> {

    DatosIndustria findByNombre(String nombre);

}
