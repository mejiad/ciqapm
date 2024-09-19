package com.evoltech.ciqapm.service;

import com.evoltech.ciqapm.model.Proyecto;
import com.evoltech.ciqapm.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProyectoServicio {

    @Autowired
    ProyectoRepository proyectoRepository;

    public ProyectoServicio(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    public Proyecto save(Proyecto proyecto){
        Proyecto newProyecto = proyectoRepository.save(proyecto);
        return newProyecto;
    }

}
