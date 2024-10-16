package com.evoltech.ciqapm.model;

import com.evoltech.ciqapm.model.datos.DatosConahcyt;
import com.evoltech.ciqapm.model.datos.DatosIndustria;
import com.evoltech.ciqapm.model.datos.DatosInternos;
import com.evoltech.ciqapm.model.datos.DatosPostgrado;
import com.evoltech.ciqapm.model.jpa.BaseClass;
import com.evoltech.ciqapm.service.EtapaService;
import com.evoltech.ciqapm.service.ProyectoServicio;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

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

    private String nombre;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    private TipoProyecto tipoProyecto;

    @Enumerated(EnumType.STRING)
    private Estado estatus;

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
    public void  proyectLoad() {
        this.avance = calculaAvance();
    }

    @PostUpdate
    public void logUserUpdate() {
        this.avance = calculaAvance();
    }
}
