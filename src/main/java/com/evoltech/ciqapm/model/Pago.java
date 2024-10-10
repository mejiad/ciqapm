package com.evoltech.ciqapm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class Pagos {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name="proyecto", nullable=false)
    private Proyecto proyecto;

    private LocalDate fechaFacturacion;

    private Integer porcentajePago;

    private PagosEstado estado;

}
