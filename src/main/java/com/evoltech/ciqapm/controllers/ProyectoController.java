package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.Proyecto;
import com.evoltech.ciqapm.model.TipoProyecto;
import com.evoltech.ciqapm.service.ProyectoServicio;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

        Proyecto p1 = new Proyecto();
        p1.setNombre("Proyecto uno");
        p1.setDescripcion("Descripcion proyeto  uno");
        p1.setTipoProyecto(TipoProyecto.INDUSTRIA);
        Proyecto p2 = new Proyecto();
        p2.setNombre("Proyecto dos");
        p2.setDescripcion("Descripcion proyecto  dos");
        p2.setTipoProyecto(TipoProyecto.INTERNOS);

        List<Proyecto> list = List.of(p1,p2);

        model.addAttribute("proyectos", list);

        return "/Proyecto/List";
    }

    @GetMapping("/view")
    public String viewProyecto(Model model) {
        return "/Proyecto/View";
    }

    @GetMapping("/edit")
    public String editProyecto(Model model) {
        return "/Proyecto/Edit";
    }

    @GetMapping("/new")
    public String newProyecto(Model model) {
        return "/Proyecto/Edit";
    }

    @PostMapping("/save")
    public String saveProyecto(Model model) {
        return "/Proyecto/List";
    }
}
