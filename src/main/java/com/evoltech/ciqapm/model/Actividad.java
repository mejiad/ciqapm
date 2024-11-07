package com.evoltech.ciqapm.model;

import com.evoltech.ciqapm.model.jpa.BaseClass;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
public class Actividad extends BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "El nombre de la actividad es un dato requerido.")
    @NotNull(message = "El nombre de la actividad es un dato requerido.")
    private String nombre;

    @NotBlank(message = "La descripción de la actividad es un dato requerido.")
    private String descripcion;

    @NotNull(message = "La fecha de inicio de la actividad es un dato requerido.")
    private LocalDate fechaInicio;

    @NotNull(message = "El estado de la actividad es un dato requerido.")
    @Enumerated(EnumType.STRING)
    private ActividadEstado estado;

    @NotNull(message = "El numero de horas de la actividad es un dato requerido.")
    private Integer horas;

    @ManyToOne
    @JoinColumn(name="etapa", nullable=false)
    private Etapa etapa;

    @ManyToOne
    @JoinColumn(name="realizado_por", nullable=true)
    private Personal realizadoPor;
}
