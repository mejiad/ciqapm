package com.evoltech.ciqapm.model.datos;

import com.evoltech.ciqapm.model.Alumno;
import com.evoltech.ciqapm.model.Proyecto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class DatosPostgrado {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Alumno alumno;

    private String notas;

    @OneToOne
    @JoinColumn(name="proyecto_id", nullable=false)
    private Proyecto proyecto;

}
