package com.evoltech.ciqapm.dto;

import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.model.datos.DatosIndustria;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IndustriaDto {

    @Id
    private Long id;

    @Size(min = 1, max = 120, message = "El nombre debe ser menor de 120 caracteres.")
    @NotNull(message = "El nombre del proyecto es requerido.")
    private String nombre;

    @Size(min = 1, max = 250, message = "La descripción debe ser menor de 250 caracteres.")
    @NotNull(message = "La descripción del proyecto es requerida.")
    private String descripcion;

    @NotNull(message = "El tipo del proyecto no se ha seleccionado.")
    @Enumerated(EnumType.STRING)
    private TipoProyecto tipoProyecto;

    @NotNull(message = "El estatus del proyecto no se ha seleccionado.")
    @Enumerated(EnumType.STRING)
    private Estado estatus;

    @NotNull(message = "El responsable del proyecto no se ha seleccionado.")
    @ManyToOne
    @JoinColumn(name="responsable_id", nullable=false)
    private Personal responsable;

    @ManyToOne
    @JoinColumn(name="cliente_id", nullable=true)
    private Cliente cliente;

    private Double presupuesto;


    public IndustriaDto(Proyecto proyecto, DatosIndustria datosIndustria){
        this.setCliente(datosIndustria.getCliente());

        this.setId(proyecto.getId());
        this.setDescripcion(proyecto.getDescripcion());
        this.setNombre(proyecto.getNombre());
        this.setEstatus(proyecto.getEstatus());
        this.setPresupuesto(datosIndustria.getPresupuesto());
        this.setResponsable(proyecto.getResponsable());
        this.setTipoProyecto(proyecto.getTipoProyecto());
    }


}
