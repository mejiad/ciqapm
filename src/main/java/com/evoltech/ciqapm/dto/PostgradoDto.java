package com.evoltech.ciqapm.dto;

import com.evoltech.ciqapm.model.Alumno;
import com.evoltech.ciqapm.model.Proyecto;
import com.evoltech.ciqapm.model.TipoProyecto;
import com.evoltech.ciqapm.model.datos.DatosPostgrado;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostgradoDto  extends ProyectoAbstractDto {

    @NotNull(message = "El alumno debe seleccionarse")
    private Alumno alumno;

    private String notas;


    public Proyecto getProyecto(){
        Proyecto proyecto = new Proyecto();

        proyecto.setId(this.getId());
        proyecto.setEstatus(this.getEstatus());
        proyecto.setTipoProyecto(TipoProyecto.POSTGRADO);
        proyecto.setDescripcion(this.getDescripcion());
        proyecto.setNombre(this.getNombre());
        proyecto.setResponsable(this.getResponsable());

        return proyecto;
    }

    public DatosPostgrado getDatosPostgrado(){
        DatosPostgrado datosPostgrado = new DatosPostgrado();
        datosPostgrado.setAlumno(this.getAlumno());
        datosPostgrado.setNotas(this.getNotas());

        return datosPostgrado;
    }
}
