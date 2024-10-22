package com.evoltech.ciqapm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.dialect.function.DB2SubstringFunction;

@Data
@Entity
@NoArgsConstructor
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "El nombre debe ser digitado.")
    private String nombre;

    @NotNull(message = "La matricula debe ser digitada.")
    private String matricula;

    @NotNull(message = "El email debe ser digitado.")
    private String email;

}
