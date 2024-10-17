package com.evoltech.ciqapm.model;

import com.evoltech.ciqapm.model.jpa.BaseClass;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Proyecto extends BaseClass implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Size(min = 1, max = 120, message = "El nombre debe ser menor de 120 caracteres.")
    @NotNull(message = "El nombre del proyecto es requerido.")
    private String nombre;

    @Size(min = 1, max = 250, message = "La descripción debe ser menor de 250 caracteres.")
    @NotNull(message = "La descripción del proyecto es requerida.")
    private String descripcion;

    @NotNull(message = "El tipo del proyecto no se ha seleccionado.")
    @Enumerated(EnumType.STRING)
    private TipoProyecto tipoProyecto;

    @NotNull(message = "El estatus del proyecto no se ha seleccionado.")
    @Enumerated(EnumType.STRING)
    private Estado estatus;

    @NotNull(message = "El responsable del proyecto no se ha seleccionado.")
    @ManyToOne
    @JoinColumn(name="responsable_id", nullable=false)
    private Personal responsable;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Etapa> etapas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="cliente_id", nullable=true)
    private Cliente cliente;

    @Transient
    private int avance;

    public void addEtapa(Etapa etapa){
        this.etapas.add(etapa);
    }

    private int calculaAvance() {
        int pct = 0;
        List<Etapa> etapas = this.getEtapas();

        int etapaSum = etapas.stream().mapToInt(Etapa::getPctCompleto).sum();
        if (etapas.size() > 0){
            pct = etapaSum / etapas.size();
        }
        return pct;
    }

    @PostLoad
    public void  postLoadAvance() {
        this.avance = calculaAvance();
    }

    @PostUpdate
    public void postUpdateAvance() {
        this.avance = calculaAvance();
    }
}
