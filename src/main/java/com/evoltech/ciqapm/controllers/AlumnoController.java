package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.Alumno;
import com.evoltech.ciqapm.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
