package com.evoltech.ciqapm.dto;

import com.evoltech.ciqapm.model.Empleado;
import com.evoltech.ciqapm.model.Estado;
import com.evoltech.ciqapm.model.Proyecto;

import java.time.LocalDate;

public interface PropuestaDtoInterface {

    Long getId();
    String getNombre();
    String getDescripcion();
    Empleado getResponsable();
    LocalDate getFechaCargaDocumento();
    Proyecto getProyecto();
    Estado getEstado();
}
