package com.evoltech.ciqapm.model;

import com.evoltech.ciqapm.model.jpa.Convocatoria;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.ToOne;

@Entity
@Data
@NoArgsConstructor
public class Interno extends Proyecto {

    @NotNull
    private String propuesta;

    @NotNull(message = "La convocatoria es un dato requerido.")
    @ManyToOne
    private Convocatoria convocatoria;

    @NotNull(message = "El objetivo es un dato requerido.")
    private String objetivo;

    // @NotNull(message = "La fase es requerida.")
    // TODO: Inicializar la fase
    @Enumerated(EnumType.STRING)
    private FaseConahcyt fase;
}
