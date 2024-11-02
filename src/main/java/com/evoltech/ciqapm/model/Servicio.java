package com.evoltech.ciqapm.model;

import com.evoltech.ciqapm.model.jpa.BaseClass;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class Servicio extends BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "La clave es un dato requerido.")
    @NotBlank(message = "La clave es un dato requerido.")
    private String clave;

    @Size(min = 1, max = 500, message = "La descripción debe ser menor de 500 caracteres.")
    @NotNull(message = "La descripción del servicio es requerida.")
    @NotBlank(message = "La descripción del servicio es requerida.")
    private String descripcion;

    @NotNull(message = "La clave SAT es un dato requerido.")
    @NotBlank(message = "La clave SAT es un dato requerido.")
    private String cveSat;

    @NotNull(message = "El costo es un dato requerido.")
    private Double costo;

    @NotNull(message = "El precio interno es un dato requerido.")
    private Double precioInt;

    @NotNull(message = "El precio de venta es un dato requerido.")
    private Double precioVta;

    public void setClave(@NotNull(message = "La clave es un dato requerido.") @NotBlank(message = "La clave es un dato requerido.") String clave) {
        this.clave = clave.toUpperCase();
    }

    public void setDescripcion(@Size(min = 1, max = 500, message = "La descripción debe ser menor de 500 caracteres.") @NotNull(message = "La descripción del servicio es requerida.") @NotBlank(message = "La descripción del servicio es requerida.") String descripcion) {
        this.descripcion = descripcion.toUpperCase();
    }

    public void setCveSat(@NotNull(message = "La clave SAT es un dato requerido.") @NotBlank(message = "La clave SAT es un dato requerido.") String cveSat) {
        this.cveSat = cveSat.toUpperCase();
    }
}
