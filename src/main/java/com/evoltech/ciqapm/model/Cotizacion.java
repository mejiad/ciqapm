package com.evoltech.ciqapm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "La clave es requerida.")
    private String numero;

    @ManyToOne
    private Industria industria;

    @NotNull(message = "El nombre es obligatorio.")
    private String nombre;

    @NotNull(message = "El costo de material debe ser introducido.")
    private Double materiales;

    @NotNull(message = "El costo de material debe ser introducido.")
    private Double viaticos;

    @NotNull(message = "El costo de material debe ser introducido.")
    private Double pasajes;

    private Double totalServicios;

    private Double costoTotalHrsHombre;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Servicio> servicios = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Empleado> empleados = new ArrayList<>();

    private Double costoTotal;

    private Double overhead;

    private Double costoTotalFinal;

    private Double utilidad;

    private Double precioMinimoVenta;

    private void addServicio(Servicio servicio){
        servicios.add(servicio);
    }

    private void addEmpleado(Empleado empleado){
        empleados.add(empleado);
    }

    private BigDecimal costoTotalServicios(){
        /*
        return servicioList.stream()
                .map(servicio -> BigDecimal.valueOf(servicio.getPrecioVta()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

         */
        return new BigDecimal("20.30");
    }

}
