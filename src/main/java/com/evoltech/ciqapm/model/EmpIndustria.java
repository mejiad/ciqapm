package com.evoltech.ciqapm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class EmpIndustria {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name="industria_id", nullable=false)
    private Industria industria;

    @NotNull(message = "El responsable es un dato requerido.")
    @ManyToOne
    @JoinColumn(name = "responsable_id", nullable = false)
    private Empleado empleado;

    private Integer horas;
}
