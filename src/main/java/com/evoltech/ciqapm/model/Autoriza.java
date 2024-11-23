package com.evoltech.ciqapm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
public class Autoriza {

    @NotNull(message = "La persona que autoriza es un dato requerido.")
    @NotBlank(message = "La persona que autoriza es un dato requerido.")
    private String persona;

    private String nota;

    private LocalDate fecha = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private AutorizaNivel nivel;

}
