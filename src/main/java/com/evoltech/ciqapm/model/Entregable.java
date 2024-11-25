package com.evoltech.ciqapm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "El nombre es un dato requerido.")
    private String nombre;

    @Size(min = 1, max = 250, message = "La descripción debe ser menor de 250 caracteres.")
    @NotNull(message = "La descripción del proyecto es requerida.")
    private String descripcion;

    private LocalDate fechaCarga;

    private String nombreArchivo;

    @Lob
    private byte[] data;

}
