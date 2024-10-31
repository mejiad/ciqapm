package com.evoltech.ciqapm.model;

import com.evoltech.ciqapm.model.jpa.BaseClass;
import jakarta.persistence.*;
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

    @NotNull(message = "El nombre es requerido")
    private String nombre;

    private String apellidos;

    @NotNull(message = "La categoria es requerida.")
    @Enumerated(EnumType.STRING)
    private PersonalCategoria categoria;

    @NotNull(message = "El rate es requerido")
    private BigDecimal rate;

    @ManyToOne
    private Etapa etapa;

    @NotNull(message = "La clave es requerida.")
    private String clave;

}
