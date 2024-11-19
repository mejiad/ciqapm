package com.evoltech.ciqapm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class EntregableEtapa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nombre;

    private String descripcion;

    private Boolean isLoaded;

    private LocalDate fechaCarga;

    @ElementCollection
    @CollectionTable
    @ManyToOne
    private Etapa etapa;

}
