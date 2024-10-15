package com.evoltech.ciqapm.model.datos;

import com.evoltech.ciqapm.model.Proyecto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class DatosIndustria {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name="proyecto_id", nullable=false)
    private Proyecto proyecto;

    private String presupuesto;
}
