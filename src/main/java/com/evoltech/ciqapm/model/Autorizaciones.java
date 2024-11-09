package com.evoltech.ciqapm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Autorizaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private NivelAutorizacion nivelAutorizacion;
    private TipoAutorizacion autorizacionDepto;
    private String nota;
    private Empleado personaAutoriza;
    private LocalDate fechaAutorizacionDepto;

}
