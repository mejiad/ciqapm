package com.evoltech.ciqapm.model.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Convocatoria extends BaseClass implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "El nombre es requerido.")
    private String nombre;

    @NotNull(message = "La descripción es requerido.")
    private String descripcion;

    @NotNull(message = "La fecha de inicio es requerida.")
    private LocalDate inicioVigencia;

    @NotNull(message = "La fecha de termino es requerida.")
    private LocalDate terminoVigencia;

}
