package com.evoltech.ciqapm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jdk.jfr.MetadataDefinition;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Entregable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Proyecto proyecto;

    private Etapa etapa;

    private String nombre;

    private Boolean isLoaded;

    private LocalDate fechaCarga;

}
