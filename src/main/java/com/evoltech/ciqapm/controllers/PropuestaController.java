package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.Propuesta;
import com.evoltech.ciqapm.model.Proyecto;
import com.evoltech.ciqapm.repository.PropuestaRepository;
import com.evoltech.ciqapm.repository.ProyectoRepository;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("propuesta")
public class PropuestaController {

    @Autowired
    PropuestaRepository propuestaRepository;

    @Autowired
    ProyectoRepository proyectoRepository;

    @GetMapping("list")
    private String lista(@RequestParam("id") Long id, Model model){
        Proyecto proyecto = proyectoRepository.getReferenceById(id);
        List<Propuesta> propuestas = propuestaRepository.findByProyecto(proyecto);

        model.addAttribute("propuestas", propuestas);
        model.addAttribute("proyecto", proyecto);

        return "Propuesta/List";
    }

    @GetMapping("new")
    private String nuevo(@RequestParam("id") Long id, Model model) {
        Proyecto proyecto = proyectoRepository.getReferenceById(id);

        Propuesta propuesta = new Propuesta();

        model.addAttribute("propuesta", propuesta);
        model.addAttribute("proyecto", proyecto);

        return "Propuesta/Edit";
    }

    @PostMapping("save")
    private String salvar(Propuesta propuesta, BindingResult result, Model model) {

       Propuesta res = propuestaRepository.save(propuesta) ;

       return "redirect:conahcyt/view/" + res.getProyecto().getId();

    }

}
