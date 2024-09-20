package com.evoltech.ciqapm.model;

import com.evoltech.ciqapm.model.jpa.BaseClass;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class Personal extends BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nombre;

    private String apellidos;

    @Enumerated(EnumType.STRING)
    private PersonalCategoria categoria;

    private BigDecimal rate;

}
