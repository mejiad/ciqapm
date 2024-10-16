package com.evoltech.ciqapm.service;

import com.evoltech.ciqapm.model.Etapa;
import com.evoltech.ciqapm.model.Proyecto;
import com.evoltech.ciqapm.repository.EtapaRepository;
import com.evoltech.ciqapm.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProyectoServicio {

    @Autowired
    ProyectoRepository proyectoRepository;

    public ProyectoServicio(ProyectoRepository proyectoRepository
                            ) {
        this.proyectoRepository = proyectoRepository;
        // this.etapaRepository = etapaRepository;
    }

    public Proyecto save(Proyecto proyecto){
        Proyecto newProyecto = proyectoRepository.save(proyecto);
        return newProyecto;
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
