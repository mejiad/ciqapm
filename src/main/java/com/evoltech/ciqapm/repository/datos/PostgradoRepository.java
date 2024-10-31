package com.evoltech.ciqapm.repository.datos;

import com.evoltech.ciqapm.model.Proyecto;
import com.evoltech.ciqapm.model.datos.DatosConahcyt;
import com.evoltech.ciqapm.model.datos.DatosIndustria;
import com.evoltech.ciqapm.model.datos.DatosPostgrado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgradoRepository extends JpaRepository<DatosPostgrado, Long> {
    DatosPostgrado findByProyecto(Proyecto proyecto);

}
