package com.evoltech.ciqapm.model;

import com.evoltech.ciqapm.model.jpa.BaseClass;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Actividad extends BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nombre;

    private String descripcion;

    private Servicio servicio;

    @Enumerated(EnumType.STRING)
    private ActividadEstado estado;

    private Date fechaInicio;

    private Date fechaTerminacion;

    private Proyecto proyecto;

    private Etapa etapa;

    private Personal usuario;
}
