package com.evoltech.ciqapm.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class EmpConahcyt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name="conahcyt_id", nullable=false)
    private Proyecto conahcyt;

    @NotNull(message = "El responsable es un dato requerido.")
    @ManyToOne
    @JoinColumn(name = "responsable_id", nullable = false)
    private Empleado empleado;

    private Integer horas;
}
