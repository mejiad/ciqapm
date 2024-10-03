package com.evoltech.ciqapm.model;

import com.evoltech.ciqapm.model.jpa.BaseClass;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Proyecto extends BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nombre;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    private TipoProyecto tipoProyecto;

    @Enumerated(EnumType.STRING)
    private Estado estatus;

    @ManyToOne
    @JoinColumn(name="responsable_id", nullable=false)
    private Personal responsable;

    @OneToMany(mappedBy = "proyecto")
    private List<Etapa> etapas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="cliente_id", nullable=false)
    private Cliente cliente;

}
