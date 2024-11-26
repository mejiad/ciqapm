package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.Alumno;
import com.evoltech.ciqapm.repository.AlumnoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/alumno")
public class AlumnoController {

    @Autowired
    AlumnoRepository alumnoRepository;

    @GetMapping("/list")
    public String alumnos(Model model){
        List<Alumno> alumnos = alumnoRepository.findAll();
        model.addAttribute("alumnos", alumnos);

        return "Alumno/List";
    }


    @GetMapping("/new")
    public String newAlumno(Model model){
        Alumno alumno = new Alumno();
        model.addAttribute("alumno", alumno);

        return "Alumno/Edit";
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveAlumno(@Valid Alumno alumno, BindingResult result, Model model){

        var mod = result.getModel();
        if(result.hasErrors()){
            System.out.println("++ Hay errores en el alumno ++++++++++++++");
            if (!mod.isEmpty()) {
                mod.forEach((String k, Object obj) -> {
                    System.out.println("Error key: " + k + " Obj:" + obj);
                    System.out.println("++++++++++++++++++++++++");
                });
            }

            model.addAttribute("alumno", alumno);
            return "Alumno/Edit";
        } else {
            System.out.println("Inicio del save");
            alumnoRepository.save(alumno);
            return "redirect:/alumno/list";

        }
    }

    @GetMapping("/edit")
    public String editAlumno(@RequestParam("id") Long id, Model model){
        Alumno alumno = alumnoRepository.getReferenceById(id);

        model.addAttribute("alumno", alumno);
        return "Alumno/Edit";
    }
}
