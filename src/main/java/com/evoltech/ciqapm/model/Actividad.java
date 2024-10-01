package com.evoltech.ciqapm.model;

import com.evoltech.ciqapm.model.jpa.BaseClass;
import jakarta.persistence.*;
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

    private String nombreActividad;

    private String descripcion;

    private Servicio servicio;

    private LocalDate fechaInicio;

    private ActividadEstado estado;

    private LocalDate fechaTerminacion;

    private Integer horasUtilizadas;

    @ManyToOne
    @JoinColumn(name="etapa_id", nullable=false)
    private Etapa etapa;

    @ManyToOne
    @JoinColumn(name="realizado_por", nullable=false)
    private Personal realizadoPor;
}
