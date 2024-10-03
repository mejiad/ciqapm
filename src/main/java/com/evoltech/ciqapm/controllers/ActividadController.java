package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.Actividad;
import com.evoltech.ciqapm.model.Etapa;
import com.evoltech.ciqapm.repository.ActividadRepository;
import com.evoltech.ciqapm.repository.EtapaRepository;
import com.evoltech.ciqapm.repository.ProyectoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/actividad")
public class ActividadController {

    @Autowired
    ProyectoRepository proyectoRepository;

    @Autowired
    EtapaRepository etapaRepository;

    @Autowired
    ActividadRepository actividadRepository;

    public ActividadController(ProyectoRepository proyectoRepository,
                               EtapaRepository etapaRepository,
                               ActividadRepository actividadRepository) {
        this.proyectoRepository = proyectoRepository;
        this.etapaRepository = etapaRepository;
        this.actividadRepository = actividadRepository;
    }

    @GetMapping("/list")
    public String listActividad(@RequestParam Long id, Model model){

        Etapa etapa = etapaRepository.getReferenceById(id);
        List<Actividad> actividades = actividadRepository.findByEtapa(etapa);
        model.addAttribute("etapa", etapa);
        model.addAttribute("actividades", actividades);

        return "/Actividad/List";
    }

    @GetMapping("/view")
    public String viewActividad(@RequestParam("id") Long id, Model model){
        Actividad actividad = new Actividad();
        try {
            actividad = actividadRepository.getReferenceById(id);
        } catch(Exception e){
            log.error("Error al leer Actividad con id:" + id);
        }
        model.addAttribute("actividad", actividad);
        return "Actividad/View";
    }

    @GetMapping("/edit")
    public String editActividad(@RequestParam Long id,  Model model){
        Actividad actividad = actividadRepository.getReferenceById(id);
        Etapa etapa = etapaRepository.getReferenceById(actividad.getEtapa().getId());
        model.addAttribute("actividad", actividad);
        model.addAttribute("etapa", etapa);
        System.out.println("Actividad nombre:" + actividad.getNombre());
        System.out.println("Actividad de la etapa:" + actividad.getEtapa().getNombre());
        return "/Actividad/Edit";
    }

    @GetMapping("/new")
    public String newActividad(@RequestParam("id") Long id, Model model){
        Etapa etapa = etapaRepository.getReferenceById(id);
        Actividad actividad = new Actividad();
        actividad.setEtapa(etapa);
        model.addAttribute("etapa", etapa);
        model.addAttribute("actividad", actividad);

        return "/Actividad/Edit";
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveActividad(Actividad actividad, Model model){

        System.out.println("Valor de etapa.proyecto: " + actividad.getEtapa().getId());
        System.out.println("ID: " + actividad.getId());
        actividadRepository.save(actividad);

        return "redirect:/proyecto/list";
    }
}
