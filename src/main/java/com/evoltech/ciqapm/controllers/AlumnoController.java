package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.Alumno;
import com.evoltech.ciqapm.repository.AlumnoRepository;
import jakarta.validation.Valid;
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
@RequestMapping("/alumno")
public class AlumnoController {

    @Autowired
    AlumnoRepository alumnoRepository;

    @GetMapping("/list")
    public String alumnos(Model model){
        List<Alumno> alumnos = alumnoRepository.findAll();
        model.addAttribute("alumnos", alumnos);

        return "/Alumno/List";
    }


    @GetMapping("/new")
    public String newAlumno(Model model){
        Alumno alumno = new Alumno();
        model.addAttribute("alumno", alumno);

        return "/Alumno/Edit";
    }

    @PostMapping("/save")
    public String saveAlumno(@Valid Alumno alumno, BindingResult result, Model model){
        if (result.hasErrors()){
            System.out.println("Hay errores");
            return "/Alumno/Edit";
        } else {
            alumnoRepository.save(alumno);
            List<Alumno> alumnos = alumnoRepository.findAll();
            model.addAttribute("alumnos", alumnos);
            return "Alumno/List";
        }
    }

    @GetMapping("/edit")
    public String editAlumno(@RequestParam("id") Long id, Model model){
        Alumno alumno = alumnoRepository.getReferenceById(id);

        model.addAttribute("alumno", alumno);
        return "Alumno/Edit";
    }
}
