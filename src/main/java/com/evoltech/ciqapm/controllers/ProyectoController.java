package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.service.ProyectoServicio;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/proyecto")
public class ProyectoController {

    private static final Logger log = LogManager.getLogger(ProyectoController.class);

    public ProyectoController(ProyectoServicio proyectoServicio) {
        this.proyectoServicio = proyectoServicio;
    }

    @Autowired
    ProyectoServicio proyectoServicio;

    @GetMapping("/list")
    public String listProyecto(Model model) {
        return "/Proyecto/List";
    }

    @GetMapping("/view")
    public String viewProyecto(Model model) {
        return "/Proyecto/List";
    }

    @GetMapping("/edit")
    public String editProyecto(Model model) {
        return "/Proyecto/List";
    }

    @PostMapping("/save")
    public String saveProyecto(Model model) {
        return "/Proyecto/List";
    }
}
