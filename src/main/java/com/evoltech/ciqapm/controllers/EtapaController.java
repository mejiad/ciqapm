package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.Etapa;
import com.evoltech.ciqapm.model.Proyecto;
import com.evoltech.ciqapm.repository.EtapaRepository;
import com.evoltech.ciqapm.repository.ProyectoRepository;
import com.evoltech.ciqapm.service.EtapaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/etapa")
public class EtapaController {

    @Autowired
    EtapaService etapaService;

    @Autowired
    EtapaRepository etapaRepository;

    @Autowired
    ProyectoRepository proyectoRepository;

    public EtapaController(EtapaService etapaService, EtapaRepository etapaRepository,
                           ProyectoRepository proyectoRepository) {
        this.etapaService = etapaService;
        this.etapaRepository = etapaRepository;
        this.proyectoRepository = proyectoRepository;
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
    public String viewEtapa(Model model){
        return "/Etapa/View";
    }

    @GetMapping("/edit")
    public String editEtapa(@RequestParam Long id, Model model){
        Etapa etapa = etapaRepository.getReferenceById(id);
        Proyecto proyecto = etapa.getProyecto();
        model.addAttribute("etapa", etapa);
        model.addAttribute("proyecto", proyecto);
        return "/Etapa/Edit";
    }

    @GetMapping("/new")
    public String newEtapa(@RequestParam Long id,  Model model) {
        Proyecto proyecto = proyectoRepository.getReferenceById(id);
        Etapa etapa = new Etapa();
        etapa.setProyecto(proyecto);
        model.addAttribute("etapa", etapa);
        model.addAttribute("proyecto", proyecto);
        return "/Etapa/Edit";
    }

   @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
   public String saveEtapa(Etapa etapa,  Model model){
        System.out.println("Valor de etapa.proyecto: " + etapa.getProyecto().getNombre());
        etapaRepository.save(etapa);
        return "redirect:/proyecto/list";
    }
}
