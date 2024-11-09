package com.evoltech.ciqapm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Propuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nombre;

    private String descripcion;

    private Empleado responsable;

    private LocalDate fechaCargaDocumento;

    @ManyToOne
    @JoinColumn(name="proyecto", nullable=false)
    private Proyecto proyecto;

    @Enumerated(EnumType.STRING)
    private Estado estado;

}
