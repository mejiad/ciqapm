package com.evoltech.ciqapm.model;

import com.evoltech.ciqapm.model.jpa.BaseClass;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Etapa extends BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name="proyecto_id", nullable=false)
    private Proyecto proyecto;

    private String nombre;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = true)
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "responsable_id", nullable = false)
    private Personal responsable;

    private String entregable;

    private LocalDate fechaEstimadaInicio;

    private LocalDate fechaRealInicio;

    private LocalDate fechaEstimadaTerminacion;

    private LocalDate fechaRealTerminacion;

    @OneToMany(mappedBy = "etapa")
    private Set<Actividad> actividades;

}
