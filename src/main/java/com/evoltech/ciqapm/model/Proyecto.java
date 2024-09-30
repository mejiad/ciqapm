package com.evoltech.ciqapm.model;

import com.evoltech.ciqapm.model.jpa.BaseClass;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

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
    @JoinColumn(name="responsable_id", nullable=false)
    private Personal responsable;

    @OneToMany(mappedBy = "proyecto")
    private Set<Etapa> etapas;

    @ManyToOne
    @JoinColumn(name="cliente_id", nullable=false)
    private Cliente cliente;

}
