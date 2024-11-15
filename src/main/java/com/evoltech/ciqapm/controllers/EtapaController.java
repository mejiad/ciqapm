package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.repository.EtapaRepository;
import com.evoltech.ciqapm.repository.EmpleadoRepository;
import com.evoltech.ciqapm.repository.ProyectoRepository;
import com.evoltech.ciqapm.repository.ServicioRepository;
import com.evoltech.ciqapm.service.EtapaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/etapa")
public class EtapaController {

    @Autowired
    EtapaService etapaService;

    @Autowired
    EtapaRepository etapaRepository;

    @Autowired
    ProyectoRepository proyectoRepository;
    private final EmpleadoRepository personalRepository;
    private final ServicioRepository servicioRepository;

    public EtapaController(EtapaService etapaService, EtapaRepository etapaRepository,
                           ProyectoRepository proyectoRepository,
                           EmpleadoRepository personalRepository,
                           ServicioRepository servicioRepository) {
        this.etapaService = etapaService;
        this.etapaRepository = etapaRepository;
        this.proyectoRepository = proyectoRepository;
        this.personalRepository = personalRepository;
        this.servicioRepository = servicioRepository;
    }

    @GetMapping("/list")
    public String listEtapa(@RequestParam("id") Long id, Model model){
        System.out.println("Numero de proyecto desde etapa:" + id);
        Proyecto proyecto = proyectoRepository.getReferenceById(id);
        model.addAttribute("proyecto", proyecto);
        List<Etapa> etapas = etapaRepository.findByProyecto(proyecto);
        System.out.println("Proyecto nombre:" + proyecto.getNombre());
        System.out.println("Etapas:" + etapas.size());
        model.addAttribute("etapas", etapas);

        return "/Etapa/List";
    }

    @GetMapping("/view")
        public String viewEtapa(@RequestParam Long id, Model model){
            Etapa etapa = etapaRepository.getReferenceById(id);
            List<Estado> estados = List.of(Estado.values());
            List<Empleado> personas = personalRepository.findAll();
            System.out.println("------ no. de personas: " + personas.size());
            List<Servicio> servicios = servicioRepository.findAll();
            Proyecto proyecto = etapa.getProyecto();
            model.addAttribute("etapa", etapa);
            model.addAttribute("proyecto", proyecto);
            model.addAttribute("estados", estados);
            model.addAttribute("personas", personas);
            model.addAttribute("servicios", servicios);
            return "/Etapa/View";
    }

    @GetMapping("/edit")
    public String editEtapa(@RequestParam Long id, Model model){
        Etapa etapa = etapaRepository.getReferenceById(id);
        List<Estado> estados = List.of(Estado.values());
        List<Empleado> personas = personalRepository.findAll();
        System.out.println("------ no. de personas: " + personas.size());
        List<Servicio> servicios = servicioRepository.findAll();
        Proyecto proyecto = etapa.getProyecto();
        model.addAttribute("etapa", etapa);
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("estados", estados);
        model.addAttribute("personas", personas);
        model.addAttribute("servicios", servicios);
        return "/Etapa/Edit";
    }

    @GetMapping("/new")
    public String newEtapa(@RequestParam Long id,  Model model) {
        Proyecto proyecto = proyectoRepository.getReferenceById(id);
        List<Estado> estados = List.of(Estado.values());
        List<Empleado> personas = personalRepository.findAll();
        List<Servicio> servicios = servicioRepository.findAll();
        Servicio servicioDummy = servicioRepository.getReferenceById(1L);
        Etapa etapa = new Etapa();
        etapa.setProyecto(proyecto);
        etapa.setPctCompleto(0);
        // etapa.setServicio(servicioDummy);
        etapa.setEstado(Estado.CREACION);

        model.addAttribute("etapa", etapa);
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("estados", estados);
        model.addAttribute("personas", personas);
        model.addAttribute("servicios", servicios);
        return "/Etapa/Edit";
    }

   @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
   public String saveEtapa(@Valid Etapa etapa, BindingResult result, Model model) {
       System.out.println("Inicio de save");
        if(result.hasErrors()){
            var mod = result.getModel();

            List<Estado> estados = List.of(Estado.values());
            List<Empleado> personas = personalRepository.findAll();
            List<Servicio> servicios = servicioRepository.findAll();
            model.addAttribute("etapa", etapa);
            model.addAttribute("estados", estados);
            model.addAttribute("personas", personas);
            model.addAttribute("servicios", servicios);
            return "Etapa/Edit";
        }
        if (etapa.getId() == null || etapa.getId() == 0) {
            etapa.setPctCompleto(0);
            etapa.setId(0L);
            etapa.setEstado(Estado.CREACION);
            etapa.setPctCompleto(0);
            Etapa resEtapa = etapaRepository.save(etapa);
            Proyecto proyecto = resEtapa.getProyecto();
            proyecto.addEtapa(resEtapa);
            proyectoRepository.save(proyecto);
        } else {
            etapaRepository.save(etapa);
        }
        //return "redirect:/proyecto/list";
        return "redirect:/proyecto/view?id=" + etapa.getProyecto().getId();

   }

}
