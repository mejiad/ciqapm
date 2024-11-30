package com.evoltech.ciqapm.dto;

import com.evoltech.ciqapm.model.Empleado;
import com.evoltech.ciqapm.model.Estado;
import com.evoltech.ciqapm.model.Proyecto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PropuestaDto implements PropuestaDtoInterface {
    @Id
    private Long id;

    @NotNull(message = "El nombre es requerido")
    private String nombre;

    @NotNull(message = "La descripción es rhequerida")
    @Size(min = 3, max = 250)
    private String descripcion;

    @NotNull(message = "El nombre del responsable es requerido.")
    @ManyToOne
    private Empleado responsable;

    private LocalDate fechaCargaDocumento;

    @ManyToOne
    @JoinColumn(name="proyecto", nullable=false)
    private Proyecto proyecto;

    @Enumerated(EnumType.STRING)
    private Estado estado;
}
