package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.Estado;
import com.evoltech.ciqapm.model.Personal;
import com.evoltech.ciqapm.model.PersonalCategoria;
import com.evoltech.ciqapm.model.Servicio;
import com.evoltech.ciqapm.repository.PersonalRepository;
import com.evoltech.ciqapm.service.PersonalServicio;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("personal")
public class PersonalController {
    @Autowired
    PersonalServicio personalServicio;

    @Autowired
    PersonalRepository personalRepository;

    public PersonalController(PersonalServicio personalServicio, PersonalRepository personalRepository) {
        this.personalServicio = personalServicio;
        this.personalRepository = personalRepository;
    }

    @GetMapping("/list")
    public String listPersonal(Model model){

        List<Personal> personal = personalRepository.findAll();
        model.addAttribute("personal", personal);

        return "/Personal/List";
    }

    @GetMapping("/view")
    public String viewPersonal(@RequestParam("id") Long id, Model model){
        Personal persona = new Personal();
        try {
            persona = personalServicio.findById(id);
        } catch(Exception e){
            log.error("Error al leer Personal con id:" + id);
        }
        model.addAttribute("persona", persona);
        return "Personal/View";
    }

    @GetMapping("/edit")
    public String editPersonal(@RequestParam Long id, Model model){
        Personal personal = personalRepository.getReferenceById(id);
        List<PersonalCategoria> personalCategorias = List.of(PersonalCategoria.values());
        model.addAttribute("personal", personal);
        model.addAttribute("categorias", personalCategorias);
        System.out.println(" ---------- las categorias son:" + personalCategorias.size());
        return "/Personal/Edit";
    }

    @GetMapping("/new")
    public String newPersonal(Model model){
        Personal personal = new Personal();
        List<PersonalCategoria> personalCategorias = List.of(PersonalCategoria.values());
        model.addAttribute("personal", personal);
        model.addAttribute("categorias", personalCategorias);
        System.out.println(" ---------- las categorias son:" + personalCategorias.size());

        return "/Personal/Edit";
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String savePersonal(@Valid Personal personal, BindingResult result, Model model){

        var mod = result.getModel();
        if(result.hasErrors()){
            System.out.println("++ Hay errores en el empleado ++++++++++++++");
            if (!mod.isEmpty()) {
                mod.forEach((String k, Object obj) -> {
                    System.out.println("Error key: " + k + " Obj:" + obj);
                    System.out.println("++++++++++++++++++++++++");
                });
            }
            List<PersonalCategoria> personalCategorias = List.of(PersonalCategoria.values());
            model.addAttribute("categorias", personalCategorias);
            model.addAttribute("personal", personal);
            return "/personal/Edit";
        } else {
            System.out.println("Inicio del save");

            String clave = personal.getClave().toUpperCase();
            String nombre = personal.getNombre().toUpperCase();
            personal.setClave(clave);
            personal.setNombre(nombre);

            personalRepository.save(personal);
            return "redirect:/personal/list";

        }
    }


}
