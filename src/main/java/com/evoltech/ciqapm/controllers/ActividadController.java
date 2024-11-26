package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.repository.ActividadRepository;
import com.evoltech.ciqapm.repository.EmpleadoRepository;
import com.evoltech.ciqapm.repository.EtapaRepository;
import com.evoltech.ciqapm.repository.ProyectoRepository;
import com.evoltech.ciqapm.utils.BreadcrumbService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/actividad")
public class ActividadController {
    private final EmpleadoRepository personalRepository;

    @Autowired
    ProyectoRepository proyectoRepository;

    @Autowired
    EtapaRepository etapaRepository;

    @Autowired
    ActividadRepository actividadRepository;

    public ActividadController(ProyectoRepository proyectoRepository,
                               EtapaRepository etapaRepository,
                               ActividadRepository actividadRepository,
                               EmpleadoRepository personalRepository) {
        this.proyectoRepository = proyectoRepository;
        this.etapaRepository = etapaRepository;
        this.actividadRepository = actividadRepository;
        this.personalRepository = personalRepository;
    }

    @GetMapping("/list")
    public String listActividad(@RequestParam Long id, Model model){

        Etapa etapa = etapaRepository.getReferenceById(id);
        List<Actividad> actividades = actividadRepository.findByEtapa(etapa);
        model.addAttribute("etapa", etapa);
        model.addAttribute("actividades", actividades);
        System.out.println("+++++++++++++++++++++++++ Actividad/list: ");

        return "Actividad/List";
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
        List<ActividadEstado> estados = List.of(ActividadEstado.values());
        model.addAttribute("actividad", actividad);
        model.addAttribute("estados", estados);
        model.addAttribute("etapa", etapa);
        System.out.println("Actividad nombre:" + actividad.getNombre());
        System.out.println("Actividad de la etapa:" + actividad.getEtapa().getNombre());
        System.out.println("Estados de la actividad:" + estados);
        return "Actividad/Edit";
    }

    @GetMapping("/new")
    public String newActividad(@RequestParam("id") Long id, Model model){
        Etapa etapa = etapaRepository.getReferenceById(id);
        List<ActividadEstado> estados = List.of(ActividadEstado.values());
        List<Empleado> personas = personalRepository.findAll();
        Actividad actividad = new Actividad();
        actividad.setEtapa(etapa);
        BreadcrumbService breadcrumbService = new BreadcrumbService();
        String pathTipoProyecto = breadcrumbService.getPathTipoProyecto(etapa.getProyecto());
        String pathProyecto = breadcrumbService.getPathProyecto(etapa.getProyecto());
        String tagTipoProyecto = breadcrumbService.getTagTipoProyecto(etapa.getProyecto());
        String proyectoNombre = etapa.getProyecto().getNombre();

        model.addAttribute("pathTipoProyecto", pathTipoProyecto);
        model.addAttribute("pathProyecto", pathProyecto);
        model.addAttribute("tagTipoProyecto", tagTipoProyecto);
        model.addAttribute("proyectoNombre", proyectoNombre);
        model.addAttribute("estados", estados);
        model.addAttribute("etapa", etapa);
        model.addAttribute("actividad", actividad);
        model.addAttribute("personas", personas);
        System.out.println("Estados de la actividad:" + estados);

        return "Actividad/Edit";
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveActividad(@Valid Actividad actividad, BindingResult result,  Model model){
        if(result.hasErrors()){
            var mod = result.getModel();
            Etapa etapa = etapaRepository.getReferenceById(actividad.getEtapa().getId());

            BreadcrumbService breadcrumbService = new BreadcrumbService();
            String pathTipoProyecto = breadcrumbService.getPathTipoProyecto(etapa.getProyecto());
            String pathProyecto = breadcrumbService.getPathProyecto(etapa.getProyecto());
            String tagTipoProyecto = breadcrumbService.getTagTipoProyecto(etapa.getProyecto());
            String proyectoNombre = etapa.getProyecto().getNombre();
            List<ActividadEstado> estados = List.of(ActividadEstado.values());
            List<Empleado> personas = personalRepository.findAll();



            model.addAttribute("actividad", actividad);
            model.addAttribute("etapa", etapa);
            model.addAttribute("estados", estados);
            model.addAttribute("personas", personas);
            model.addAttribute("pathTipoProyecto", pathTipoProyecto);
            model.addAttribute("pathProyecto", pathProyecto);
            model.addAttribute("tagTipoProyecto", tagTipoProyecto);
            model.addAttribute("proyectoNombre", proyectoNombre);

            return "Actividad/Edit";
        }

        System.out.println("Valor de etapa.proyecto: " + actividad.getEtapa().getId());
        actividadRepository.save(actividad);
        System.out.println("+++++++++++++++++++++++++ ID: " + actividad.getId());
        return "redirect:/etapa/view?id=" + actividad.getEtapa().getId();

    }
}


