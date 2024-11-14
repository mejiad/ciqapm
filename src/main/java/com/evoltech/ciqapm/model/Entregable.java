package com.evoltech.ciqapm.model;

<<<<<<< HEAD
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jdk.jfr.MetadataDefinition;
=======
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
>>>>>>> entregables-etapa
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Entregable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @NotNull(message = "La etapa es un dato requerido.")
    @JoinColumn(name="etapa", nullable=false)
    private Etapa etapa;

    @NotNull(message = "El nombre es un dato requerido.")
    private String nombre;

    @Size(min = 0, max = 255)
    private String descripcion;

    private LocalDate fechaCarga;

}
