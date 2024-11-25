package com.evoltech.ciqapm.model;

import com.evoltech.ciqapm.model.jpa.BaseClass;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "El nombre del documento es un dato requerido.")
    @NotNull(message = "El nombre de la documento es un dato requerido.")
    private String nombre;

    @Column(length = 2000)
    @NotBlank(message = "La descripción del documento es un dato requerido.")
    private String descripcion;

    @NotBlank(message = "El archivo del documento es un dato requerido.")
    @NotNull(message = "El archivo del documento es un dato requerido.")
    private String nombreArchivo;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @ManyToOne
    private Proyecto proyecto;

    @ManyToOne
    private Etapa etapa;

    @ManyToOne
    private Entregable entregable;

    @Lob
    private byte[] data;

}
