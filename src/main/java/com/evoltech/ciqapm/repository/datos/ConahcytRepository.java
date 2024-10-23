package com.evoltech.ciqapm.repository.datos;

import com.evoltech.ciqapm.model.Proyecto;
import com.evoltech.ciqapm.model.TipoProyecto;
import com.evoltech.ciqapm.model.datos.DatosConahcyt;
import com.evoltech.ciqapm.model.datos.DatosIndustria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConahcytRepository extends JpaRepository<DatosConahcyt, Long> {

    DatosConahcyt findByProyecto(Proyecto proyecto);

}
