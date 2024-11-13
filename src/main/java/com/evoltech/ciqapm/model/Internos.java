package com.evoltech.ciqapm.model;

import com.evoltech.ciqapm.model.jpa.Convocatoria;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Internos extends Proyecto {

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
