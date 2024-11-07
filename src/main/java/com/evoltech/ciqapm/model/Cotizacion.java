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
    private Double totalServicios;

    @NotNull(message = "El costo total de horas ser introducido.")
    private Double costoTotalHrsHombre;

    /*
    @OneToMany(cascade = CascadeType.ALL)
    private List<Servicio> servicioList = new ArrayList<>();

     */

    // private List<Personal> empleados = new ArrayList<>();

    private Double costoTotal;

    private Double overhead;

    private Double costoTotalFinal;

    private Double utilidad;

    private Double precioMinimoVenta;

    /*
    private void addServicio(Servicio servicio){
        servicioList.add(servicio);
    }

     */

    /*
    private void addEmpleado(Personal personal){
        empleados.add(personal);
    }
     */

    private BigDecimal costoTotalServicios(){
        /*
        return servicioList.stream()
                .map(servicio -> BigDecimal.valueOf(servicio.getPrecioVta()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

         */
        return new BigDecimal("20.30");
    }

}
