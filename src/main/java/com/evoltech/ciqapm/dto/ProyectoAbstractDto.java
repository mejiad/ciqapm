package com.evoltech.ciqapm.dto;

import com.evoltech.ciqapm.model.Estado;
import com.evoltech.ciqapm.model.Personal;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public abstract class ProyectoAbstractDto {

    @Id
    private Long id;

    @NotNull(message = "El nombre del proyecto es requerido.")
    @NotEmpty(message = "El nombre del proyecto no permite blancos")
    private String nombre;

    @Size(min = 1, max = 250, message = "La descripción debe tener entre 1 y 250 caracteres.")
    @NotNull(message = "La descripción del proyecto es requerida.")
    @NotEmpty(message = "La descripción no permite blancos")
    private String descripcion;

    @NotNull(message = "El estatus del proyecto no se ha seleccionado.")
    @Enumerated(EnumType.STRING)
    private Estado estatus;

    @NotNull(message = "El responsable del proyecto no se ha seleccionado.")
    @ManyToOne
    @JoinColumn(name="responsable_id", nullable=false)
    private Personal responsable;
}
