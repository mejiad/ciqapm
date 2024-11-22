package com.evoltech.ciqapm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class EtapaServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;

    private Integer cantidad;

    @ElementCollection
    @CollectionTable
    @ManyToOne
    private Etapa etapa;
}
