package com.evoltech.ciqapm.model;

import com.evoltech.ciqapm.model.jpa.BaseClass;
import jakarta.persistence.*;
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

    private String clave;

    @Size(min = 1, max = 500)
    private String descripcion;

    private String cveSat;

    private Double costo;

    private Double precioInt;

    private Double precioVta;

}
