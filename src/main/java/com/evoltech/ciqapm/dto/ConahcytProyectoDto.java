package com.evoltech.ciqapm.dto;

import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.model.datos.DatosConahcyt;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConahcytProyectoDto {

    @Size(min = 1, max = 120, message = "El nombre debe ser menor de 120 caracteres.")
    @NotNull(message = "El nombre del proyecto es requerido.")
    private String nombre;

    @Size(min = 1, max = 250, message = "La descripción debe ser menor de 250 caracteres.")
    @NotNull(message = "La descripción del proyecto es requerida.")
    private String descripcion;

    @NotNull(message = "El estatus del proyecto no se ha seleccionado.")
    @Enumerated(EnumType.STRING)
    private Estado estatus;

    @NotNull(message = "El responsable del proyecto no se ha seleccionado.")
    @ManyToOne
    @JoinColumn(name="responsable_id", nullable=false)
    private Personal responsable;

    @NotNull(message = "La convocatoria es un dato requerido.")
    private String convocatoria;

    @NotNull(message = "El objetivo es un dato requerido.")
    private String objetivo;


    public Proyecto proyecto(){
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(nombre);

        proyecto.setDescripcion(descripcion);

        proyecto.setStatus(Estado.PROCESO.name());

        proyecto.setResponsable(responsable);

        proyecto.setTipoProyecto(TipoProyecto.CONAHCYT);

        return proyecto;
    }

    public DatosConahcyt conahcyt(){
       DatosConahcyt conahcyt = new DatosConahcyt();
       conahcyt.setConvocatoria(convocatoria);
       conahcyt.setObjetivo(objetivo);

       return conahcyt;
    }
}
