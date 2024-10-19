package com.evoltech.ciqapm.model.datos;

import com.evoltech.ciqapm.model.Proyecto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class DatosConahcyt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "La convocatoria es un dato requerido.")
    private String convocatoria;

    @NotNull(message = "El objetivo es un dato requerido.")
    private String objetivo;

    @OneToOne
    @JoinColumn(name="proyecto_id", nullable=false)
    private Proyecto proyecto;

}
