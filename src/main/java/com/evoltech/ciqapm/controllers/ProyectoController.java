package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.Documento;
import com.evoltech.ciqapm.model.Proyecto;
import com.evoltech.ciqapm.model.TipoProyecto;
import com.evoltech.ciqapm.repository.ProyectoRepository;
import com.evoltech.ciqapm.service.ProyectoServicio;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/proyecto")
public class ProyectoController {

    private static final Logger log = LogManager.getLogger(ProyectoController.class);

    @Autowired
    ProyectoServicio proyectoServicio;

    @Autowired
    ProyectoRepository proyectoRepository;

    public ProyectoController(ProyectoServicio proyectoServicio, ProyectoRepository proyectoRepository) {
        this.proyectoServicio = proyectoServicio;
        this.proyectoRepository = proyectoRepository;
    }

    @GetMapping("/list")
    public String listProyecto(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println("Usuario loggeado:" + username);

        List<Proyecto> proyectos = proyectoRepository.findAll();

        model.addAttribute("proyectos", proyectos);

        return "/Proyecto/List";
    }

    @GetMapping("/view")
    public String viewProyecto(@RequestParam("id") Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println("Nombre del usuario: " + username);

        Proyecto proyecto = proyectoRepository.getReferenceById(id);
        model.addAttribute("proyecto", proyecto);

        return "/Proyecto/View";
    }

    @GetMapping("/edit")
    public String editProyecto(Model model) {
        return "/Proyecto/Edit";
    }

    @GetMapping("/new")
    public String newProyecto(Model model) {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("Primer Proyecto de prueba.");
        proyecto.setDescripcion("Descripción del proyecto.");
        model.addAttribute("proyecto", proyecto);

        return "/Proyecto/Edit";
    }

    /*
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveDocumento(Documento documento, Model model){
    */

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveProyecto(Proyecto proyecto, Model model) {

        // TODO: Crear el directorio del id del proyecto
        Proyecto res = proyectoRepository.save(proyecto);

        System.out.println("ID del nuevo proyecto: " + res.getId());
        new File("src/main/resources/directory/" + res.getId()).mkdirs();
        return "redirect:/proyecto/list";
    }
}
