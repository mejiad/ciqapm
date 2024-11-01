package com.evoltech.ciqapm.model;

import com.evoltech.ciqapm.model.jpa.BaseClass;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.print.DocFlavor;

@Entity
@Data
@NoArgsConstructor
public class Documento extends BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nombre;

    @Column(length = 2000)
    private String descripcion;

    private String nombreArchivo;

    @ManyToOne
    private Proyecto proyecto;

    @Lob
    private byte[] data;

}
