package com.evoltech.ciqapm.dto.reportes.proyecto;

import com.evoltech.ciqapm.model.Estado;
import com.evoltech.ciqapm.model.Etapa;
import com.evoltech.ciqapm.model.TipoProyecto;
import java.time.LocalDate;
import java.util.List;

public record ReporteProyectoDto(String nombre, TipoProyecto tipoProyecto,
                                 LocalDate fechaCreacion, Estado estado, List<Etapa> etapas,
                                 String nombreResponsable, int avance, Long id) {
}
