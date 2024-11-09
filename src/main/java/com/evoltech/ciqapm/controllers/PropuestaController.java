package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.Estado;
import com.evoltech.ciqapm.model.Empleado;
import com.evoltech.ciqapm.model.Propuesta;
import com.evoltech.ciqapm.model.Proyecto;
import com.evoltech.ciqapm.repository.EmpleadoRepository;
import com.evoltech.ciqapm.repository.PropuestaRepository;
import com.evoltech.ciqapm.repository.ProyectoRepository;
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

    @Autowired
    EmpleadoRepository personalRepository;

    @GetMapping("list")
    private String lista(@RequestParam("id") Long id, Model model){
        Proyecto proyecto = proyectoRepository.getReferenceById(id);
        List<Propuesta> propuestas = propuestaRepository.findByProyecto(proyecto);
        List<Empleado> personas = personalRepository.findAll();
        List<Estado> estados = List.of(Estado.values());

        model.addAttribute("personas", personas);
        model.addAttribute("propuestas", propuestas);
        model.addAttribute("proyecto", proyecto);


        return "Propuesta/List";
    }

    @GetMapping("new")
    private String nuevo(@RequestParam("id") Long id, Model model) {
        Proyecto proyecto = proyectoRepository.getReferenceById(id);

        Propuesta propuesta = new Propuesta();
        List<Empleado> personas = personalRepository.findAll();
        List<Estado> estados = List.of(Estado.values());

        model.addAttribute("estados", estados);
        model.addAttribute("personas", personas);
        model.addAttribute("propuesta", propuesta);
        model.addAttribute("proyecto", proyecto);

        return "Propuesta/Edit";
    }

    @PostMapping("save")
    private String salvar(Propuesta propuesta, BindingResult result, Model model) {

       Propuesta res = propuestaRepository.save(propuesta);
        System.out.println("Regresando a:" + res.getProyecto().getNombre());

       return "redirect:/conahcyt/view/" + res.getProyecto().getId();
    }

}
