package com.evoltech.ciqapm.service;

import com.evoltech.ciqapm.model.Servicio;
import com.evoltech.ciqapm.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService {

    @Autowired
    ServicioRepository servicioRepository;

    public ServicioService(ServicioRepository servicioRepository){
        this.servicioRepository = servicioRepository;
    }

    public List<Servicio> getAll(){
        return servicioRepository.findAll();
    }

    public Servicio  save(Servicio servicio){
        Servicio newServicio = servicioRepository.save(servicio);
        return newServicio;
    }

}
