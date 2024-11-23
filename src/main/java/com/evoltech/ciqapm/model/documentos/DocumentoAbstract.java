package com.evoltech.ciqapm.model.documentos;

import com.evoltech.ciqapm.model.TipoDocumento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Data
@MappedSuperclass
public abstract class DocumentoAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "El nombre es requerido")
    @NotEmpty(message = "El nombre es requerido")
    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @NotNull(message = "La descripción es requerida")
    @NotEmpty(message = "La descripción es requerida")
    @NotBlank(message = "La descripciónes requerida")
    private String descripcion;

    @NotNull(message = "El archivo es requerido")
    @NotEmpty(message = "El archivo es requerido")
    @NotBlank(message = "El archivo es requerido")
    private String filename;

    @NotEmpty(message = "El tipo de documento es requerido")
    @NotNull(message = "El tipo de documento es requerido")
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDate fechaCarga;

    private byte[] data;

}
