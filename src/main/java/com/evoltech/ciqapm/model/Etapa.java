package com.evoltech.ciqapm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Etapa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Proyecto proyecto;

    private String nombre;

    private String descripcion;

    @ManyToOne
    private Personal responsable;   // TODO: liga a personal

    private String entregable;

    private Date fechaEstimadaInicio;

    private Date fechaRealIncio;

    private Date fechaEstimadaTerminacion;

    private Date fechaRealTerminacion;

}
