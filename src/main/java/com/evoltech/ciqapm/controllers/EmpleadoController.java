package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.Empleado;
import com.evoltech.ciqapm.model.EmpleadoCategoria;
import com.evoltech.ciqapm.repository.EmpleadoRepository;
import com.evoltech.ciqapm.service.EmpleadoServicio;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("empleado")
public class EmpleadoController {
    @Autowired
    EmpleadoServicio empleadoServicio;

    @Autowired
    EmpleadoRepository empleadoRepository;

    public EmpleadoController(EmpleadoServicio personalServicio, EmpleadoRepository personalRepository) {
        this.empleadoServicio = personalServicio;
        this.empleadoRepository = personalRepository;
    }

    @GetMapping("/list")
    public String listEmpleado(Model model){

        List<Empleado> personal = empleadoRepository.findAll();
        model.addAttribute("personal", personal);

        return "Empleado/List";
    }

    @GetMapping("/view")
    public String viewEmpleado(@RequestParam("id") Long id, Model model){
        Empleado persona = new Empleado();
        try {
            persona = empleadoServicio.findById(id);
        } catch(Exception e){
            log.error("Error al leer Empleado con id:" + id);
        }
        model.addAttribute("persona", persona);
        return "Empleado/View";
    }

    @GetMapping("/edit")
    public String editEmpleado(@RequestParam Long id, Model model){
        Empleado empleado = empleadoRepository.getReferenceById(id);
        List<EmpleadoCategoria> empleadoCategorias = List.of(EmpleadoCategoria.values());
        model.addAttribute("empleado", empleado);
        model.addAttribute("categorias", empleadoCategorias);
        System.out.println(" ---------- las categorias son:" + empleadoCategorias.size());
        return "Empleado/Edit";
    }

    @GetMapping("/new")
    public String newEmpleado(Model model){
        Empleado empleado = new Empleado();
        List<EmpleadoCategoria> empleadoCategorias = List.of(EmpleadoCategoria.values());
        model.addAttribute("personal", empleado);
        model.addAttribute("categorias", empleadoCategorias);
        System.out.println(" ---------- las categorias son:" + empleadoCategorias.size());

        return "Empleado/Edit";
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveEmpleado(@Valid Empleado empleado, BindingResult result, Model model){

        var mod = result.getModel();
        if(result.hasErrors()){
            System.out.println("++ Hay errores en el empleado ++++++++++++++");
            if (!mod.isEmpty()) {
                mod.forEach((String k, Object obj) -> {
                    System.out.println("Error key: " + k + " Obj:" + obj);
                    System.out.println("++++++++++++++++++++++++");
                });
            }
            List<EmpleadoCategoria> empleadoCategorias = List.of(EmpleadoCategoria.values());
            model.addAttribute("categorias", empleadoCategorias);
            model.addAttribute("empleado", empleado);
            return "Empleado/Edit";
        } else {
            System.out.println("Inicio del save");

            String clave = empleado.getClave().toUpperCase();
            String nombre = empleado.getNombre().toUpperCase();
            empleado.setClave(clave);
            empleado.setNombre(nombre);

            empleadoRepository.save(empleado);
            return "redirect:/empleado/list";

        }
    }


}
