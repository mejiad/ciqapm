package com.evoltech.ciqapm.service;

import com.evoltech.ciqapm.model.Etapa;
import com.evoltech.ciqapm.model.Propuesta;
import com.evoltech.ciqapm.model.Proyecto;
import com.evoltech.ciqapm.repository.EtapaRepository;
import com.evoltech.ciqapm.repository.PropuestaRepository;
import com.evoltech.ciqapm.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProyectoServicio {

    @Autowired
    ProyectoRepository proyectoRepository;
    private final PropuestaRepository propuestaRepository;

    public ProyectoServicio(ProyectoRepository proyectoRepository,
                            PropuestaRepository propuestaRepository) {
        this.proyectoRepository = proyectoRepository;
        // this.etapaRepository = etapaRepository;
        this.propuestaRepository = propuestaRepository;
    }

    public Proyecto save(Proyecto proyecto){
        Proyecto newProyecto = proyectoRepository.save(proyecto);
        return newProyecto;
    }

    public Propuesta save(Propuesta propuesta){
        var res = propuestaRepository.save(propuesta);
        return res;
    }

    //
    // el  id es del proyecto
    //
    /*
    public int calculaAvance(Long id) {
        int pct = 0;
        Proyecto proyecto = proyectoRepository.getReferenceById(id);
        List<Etapa> etapas = etapaRepository.findByProyecto(proyecto);

        int etapaSum = etapas.stream().mapToInt(Etapa::getPctCompleto).sum();
        if (etapas.size() > 0){
            pct = etapaSum / etapas.size();
        }
        return pct;
    }

     */
}
