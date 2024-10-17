package com.evoltech.ciqapm.model;

import com.evoltech.ciqapm.model.jpa.BaseClass;
import com.evoltech.ciqapm.validation.CheckEtapaDates;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@CheckEtapaDates
public class Etapa extends BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name="proyecto", nullable=false)
    private Proyecto proyecto;

    @NotBlank(message = "Nombre de la Etapa es requerido.")
    private String nombre;

    @NotBlank(message = "Descripción de la Etapa es un campo requerido.")
    private String descripcion;

    @NotNull(message = "El servicio es un dato requerido.")
    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = true)
    private Servicio servicio;

    @NotNull(message = "El responsable es un dato requerido.")
    @ManyToOne
    @JoinColumn(name = "responsable_id", nullable = false)
    private Personal responsable;

    @NotBlank(message = "El Entregable es requerido.")
    private String entregable;

    @NotNull(message = "La fecha de inicio es requerida.")
    private LocalDate fechaEstimadaInicio;

    @NotNull(message = "La fecha de terminacion es requerida.")
    private LocalDate fechaEstimadaTerminacion;

    private Integer pctCompleto;

    @NotNull(message = "El estado es requerido.")
    @Enumerated(EnumType.STRING)
    private Estado estado;
}

/*
private String taskId;        == id.toString
private String taskName;      == nombre
private String resource;      == servicio.nombre
private String startDate;     == fechaRealInicio
private String endDate;       == fechaEstimadaTerminacion
private Double pctComplete;   == pctCompleto  TODO
 */


