package com.evoltech.ciqapm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "El nombre es un dato requerido.")
    private String nombre;

    @NotBlank(message = "El R.F.C. es un dato requerido.")
    private String rfc;

    @NotBlank(message = "El nombre del contacto es un dato requerido.")
    private String nombreContacto;

    @NotBlank(message = "El telefono del contacto es un dato requerido.")
    private String telefono;

    @NotBlank(message = "El correo electrónico del contacto es un dato requerido.")
    @Email(message= "Debe capturar una dirección válida")
    private String email;

}
