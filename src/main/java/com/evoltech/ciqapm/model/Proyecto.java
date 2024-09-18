package com.evoltech.ciqapm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    private String nombre;

    private String descripcion;

    private TipoProyecto tipoProyecto;

    @ManyToOne
    private Personal responsable;

    @OneToMany
    private List<Etapa> etapas;

}
