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

    private String nombre;

    private String descripcion;

    private LocalDate fechaInicio;

    private ActividadEstado estado;

    private Integer horas;

    @ManyToOne
    @JoinColumn(name="etapa", nullable=false)
    private Etapa etapa;

    @ManyToOne
    @JoinColumn(name="realizado_por", nullable=true)
    private Personal realizadoPor;
}
