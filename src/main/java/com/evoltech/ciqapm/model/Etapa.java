package com.evoltech.ciqapm.model;

import com.evoltech.ciqapm.model.jpa.BaseClass;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Etapa extends BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    private Proyecto proyecto;

    private String nombre;

    private String descripcion;

    @OneToOne
    private Servicio servicio;

    @ManyToOne
    private Personal responsable;

    private String entregable;

    private LocalDate fechaEstimadaInicio;

    private LocalDate fechaRealInicio;

    private LocalDate fechaEstimadaTerminacion;

    private LocalDate fechaRealTerminacion;

}
