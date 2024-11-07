package com.evoltech.ciqapm.model;

import com.evoltech.ciqapm.model.jpa.BaseClass;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class Personal extends BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "El nombre es un dato requerido")
    @NotEmpty(message = "El nombre es un dato requerido.")
    private String nombre;

    private String apellidos;

    @NotNull(message = "La categoria es un dato requerido.")
    @Enumerated(EnumType.STRING)
    private PersonalCategoria categoria;

    @NotNull(message = "La tarifa es un dato requerido.")
    private BigDecimal rate;

    @ManyToOne
    private Etapa etapa;

    @NotNull(message = "La clave es un dato requerido.")
    @NotEmpty(message = "La clave es un dato requerido.")
    private String clave;

}
