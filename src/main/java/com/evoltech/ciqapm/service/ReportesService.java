package com.evoltech.ciqapm.service;

import com.evoltech.ciqapm.dto.reportes.proyecto.ReporteProyectoDto;
import com.evoltech.ciqapm.model.Etapa;
import com.evoltech.ciqapm.model.Proyecto;
import com.evoltech.ciqapm.repository.EtapaRepository;
import com.evoltech.ciqapm.repository.ProyectoRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportesService {

    private ProyectoRepository proyectoRepository;
    private EtapaRepository etapaRepository;

    public ReportesService(ProyectoRepository proyectoRepository,
                           EtapaRepository etapaRepository) {
        this.proyectoRepository = proyectoRepository;
        this.etapaRepository = etapaRepository;
    }

    public List<ReporteProyectoDto> proyectos(){

        List<ReporteProyectoDto> lista = new ArrayList<>();
        List<Proyecto> proyectos = proyectoRepository.findAll();
        proyectos.forEach(proyecto -> {
            ReporteProyectoDto pdto = new ReporteProyectoDto(proyecto.getNombre(),proyecto.getTipoProyecto(),
                    proyecto.getCreateDate(),proyecto.getEstatus(), null);
            lista.add(pdto);
        });

        return lista;
    }


    public List<ReporteProyectoDto> proyectosEtapas(){
        List<ReporteProyectoDto> dtos = new ArrayList<>();
        List<Proyecto> lista = proyectoRepository.findAll();
        lista.forEach(proyecto -> {
            List<Etapa> etapas = etapaRepository.findByProyecto(proyecto);
            if (etapas.size() > 0) {
                ReporteProyectoDto pdto = new ReporteProyectoDto(proyecto.getNombre(),proyecto.getTipoProyecto(),
                    proyecto.getCreateDate(),proyecto.getEstatus(), etapas);
                dtos.add(pdto);
            }
        });
        return dtos;
    }
}
