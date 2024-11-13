package com.evoltech.ciqapm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract  class AbstractProyecto {

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
    private Empleado responsable;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Etapa> etapas = new ArrayList<>();

    @Column
    private LocalDate createDate;

    @Column
    private LocalDate updateDate;

    @Column
    private String userUpdate;

    @Column
    private String createUser;

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
    @PrePersist
    private void prePersist(){
        createDate = LocalDate.now();
        System.out.println("++++++ PrePersist." + this.getClass().getName());
    }
}
