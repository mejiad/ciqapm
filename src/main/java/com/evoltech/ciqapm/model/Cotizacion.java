package com.evoltech.ciqapm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Cotizacion {

    // TODO: responsable, cliente, vigencia, proyecto(?)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "La clave es requerida.")
    private String numero;

    @NotNull(message = "El nombre es obligatorio.")
    private String nombre;

    @NotNull(message = "El costo de material debe ser introducido.")
    private Double materiales;

    @NotNull(message = "El costo de material debe ser introducido.")
    private Double viaticos;

    @NotNull(message = "El costo de material debe ser introducido.")
    private Double pasajes;

    @NotNull(message = "El costo total pruebas debe ser introducido.")
    private Double pruebasServicios;

    @NotNull(message = "El costo total de horas ser introducido.")
    private Double costoTotalHrsHombre;

    private Double costoTotal;

    private Double overhead;

    private Double costoTotalFinal;

    private Double utilidad;

    private Double precioMinimoVenta;

}
