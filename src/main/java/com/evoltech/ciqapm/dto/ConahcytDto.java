package com.evoltech.ciqapm.dto;

import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.model.datos.DatosConahcyt;
import com.evoltech.ciqapm.model.jpa.Convocatoria;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConahcytDto {

    @Id
    private Long id;

    @NotNull(message = "El nombre del proyecto es requerido.")
    @NotEmpty(message = "No se permiten blancos")
    private String nombre;

    @Size(min = 1, max = 250, message = "La descripción debe ser menor de 250 caracteres.")
    @NotNull(message = "La descripción del proyecto es requerida.")
    @NotEmpty(message = "No se permiten blancos")
    private String descripcion;

    @NotNull(message = "El estatus del proyecto no se ha seleccionado.")
    @Enumerated(EnumType.STRING)
    private Estado estatus;

    @NotNull(message = "El responsable del proyecto no se ha seleccionado.")
    @ManyToOne
    @JoinColumn(name="responsable_id", nullable=false)
    private Personal responsable;

    @NotNull(message = "La convocatoria es un dato requerido.")
    private Convocatoria convocatoria;

    @NotNull(message = "El objetivo es un dato requerido.")
    private String objetivo;


    public Proyecto proyecto(){
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(nombre);

        proyecto.setDescripcion(descripcion);

        proyecto.setEstatus(Estado.PROCESO);

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

    public ConahcytDto(Proyecto proyecto, DatosConahcyt datosConahcyt) {
        this.id = proyecto.getId();
        this.nombre = proyecto.getNombre();
        this.descripcion = proyecto().getDescripcion();
        this.estatus = proyecto.getEstatus();
        this.responsable = proyecto.getResponsable();
        this.convocatoria = datosConahcyt.getConvocatoria();
        this.objetivo = datosConahcyt.getObjetivo();
    }
}
