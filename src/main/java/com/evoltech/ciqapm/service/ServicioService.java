package com.evoltech.ciqapm.service;

import com.evoltech.ciqapm.model.Servicio;
import com.evoltech.ciqapm.repository.ServicioPagingRepository;
import com.evoltech.ciqapm.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<Servicio> getAll(String descripcion){
        Pageable pageable = PageRequest.of(0, 20);
        return servicioRepository.findByDescripcionContains(descripcion,pageable);
    }

    public Page<Servicio> getPage(String descripcion, Integer page){
        Pageable pageable = PageRequest.of(page, 20);
        Page<Servicio> servicios = servicioRepository.findByDescripcionContains(descripcion, pageable);
        return servicios;
    }

    public Page<Servicio> getPage(Integer page){
        Pageable pageable = PageRequest.of(page, 20);
        Page<Servicio> servicios = servicioRepository.findAll(pageable);
        return servicios;
    }

    public Servicio  save(Servicio servicio){
        Servicio newServicio = servicioRepository.save(servicio);
        return newServicio;
    }

}
