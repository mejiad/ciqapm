package com.evoltech.ciqapm.model;

import com.evoltech.ciqapm.model.jpa.BaseClass;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Proyecto extends BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    private String nombre;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    private TipoProyecto tipoProyecto;

    @ManyToOne
    private Personal responsable;

    @OneToMany
    private List<Etapa> etapas;

}
