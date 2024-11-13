package com.evoltech.ciqapm.model;

import com.evoltech.ciqapm.model.jpa.Convocatoria;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Conahcyt extends Proyecto {

    @NotNull
    private String propuesta;

    @NotNull(message = "La convocatoria es un dato requerido.")
    private Convocatoria convocatoria;

    @NotNull(message = "El objetivo es un dato requerido.")
    private String objetivo;

    // @NotNull(message = "La fase es requerida.")
    // TODO: Inicializar la fase
    @Enumerated(EnumType.STRING)
    private FaseConahcyt fase;

}
