package com.evoltech.ciqapm.model.datos;

import com.evoltech.ciqapm.model.Proyecto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class DatosPostgrado {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String alumno;

    private String notas;

    @OneToOne
    @JoinColumn(name="proyecto_id", nullable=false)
    private Proyecto proyecto;

}
