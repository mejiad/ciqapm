package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.Actividad;
import com.evoltech.ciqapm.repository.ActividadRepository;
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
    ActividadRepository actividadRepository;

    public ActividadController(ActividadRepository documentoRepository) {
        this.actividadRepository = actividadRepository;
    }

    @GetMapping("/list")
    public String listActividad(Model model){

        List<Actividad> actividad = actividadRepository.findAll();
        model.addAttribute("actividad", actividad);

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
    public String editActividad(Model model){
        return "/Actividad/Edit";
    }

    @GetMapping("/new")
    public String newActividad(Model model){
        Actividad actividad = new Actividad();
        model.addAttribute("actividad", actividad);

        return "/Actividad/Edit";
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveActividad(Actividad actividad, Model model){

        System.out.println("ID: " + actividad.getId());
        actividadRepository.save(actividad);

        return "redirect:/actividad/list";
    }
}
