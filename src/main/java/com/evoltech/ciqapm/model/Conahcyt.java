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
    @ManyToOne
    private Convocatoria convocatoria;

    @NotNull(message = "El objetivo es un dato requerido.")
    private String objetivo;

    // @NotNull(message = "La fase es requerida.")
    // TODO: Inicializar la fase
    @Enumerated(EnumType.STRING)
    private FaseConahcyt fase;

    // @Embedded
    //Autoriza autorizaciondepto;

    @Embedded
    @AttributeOverride(name = "persona",
            column = @Column(name = "deptoAutoriza"))
    @AttributeOverride(name = "nota",
            column = @Column(name = "deptoNota"))
    @AttributeOverride(name = "fecha",
            column = @Column(name = "deptoFecha"))
    @AttributeOverride(name = "nivel",
            column = @Column(name = "deptoNivel"))
    public  Autoriza deptoAutoriza;

    @Embedded
    @AttributeOverride(name = "persona",
            column = @Column(name = "invAutoriza"))
    @AttributeOverride(name = "nota",
            column = @Column(name = "invNota"))
    @AttributeOverride(name = "fecha",
            column = @Column(name = "invFecha"))
    @AttributeOverride(name = "nivel",
            column = @Column(name = "invNivel"))
    public  Autoriza invAutoriza;

    @Embedded
    @AttributeOverride(name = "persona",
            column = @Column(name = "dirAutoriza"))
    @AttributeOverride(name = "nota",
            column = @Column(name = "dirNota"))
    @AttributeOverride(name = "fecha",
            column = @Column(name = "dirFecha"))
    @AttributeOverride(name = "nivel",
            column = @Column(name = "dirNivel"))
    public  Autoriza dirAutoriza;
    /*
    @Embedded
    @AttributeOverride(name = "invAutoriza",
            column = @Column(name = "persona_autoriza"))
    @AttributeOverride(name = "invNota",
            column = @Column(name = "nota_autoriza"))
    @AttributeOverride(name = "invFecha",
            column = @Column(name = "fecha_autoriza"))
    @AttributeOverride(name = "invNivel",
            column = @Column(name = "nivel_autorizacion"))
    private Autoriza investigacionAutoriza;
     */
}
