package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.Personal;
import com.evoltech.ciqapm.model.PersonalCategoria;
import com.evoltech.ciqapm.repository.PersonalRepository;
import com.evoltech.ciqapm.service.PersonalServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
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
    public String editPersonal(Model model){
        return "/Personal/Edit";
    }

    @GetMapping("/new")
    public String newPersonal(Model model){
        Personal personal = new Personal();
        model.addAttribute("persona", personal);

        return "/Personal/Edit";
    }

    /*
    @PostMapping(value = "/save")
    public String savePersonal(@RequestParam MultiValueMap body, Model model){
        System.out.println("Nombre en save:" + body.getFirst("nombre"));
        Personal personal = new Personal();
        personal.setId(0L);
        personal.setNombre(new String(String.valueOf(body.getFirst("nombre"))));
        personal.setApellidos(new String(String.valueOf(body.getFirst("apellidos"))));
        personal.setCategoria(PersonalCategoria.valueOf(new String(String.valueOf(body.getFirst("categoria")))));
        personal.setRate(new BigDecimal(String.valueOf(body.getFirst("rate"))));
        personalRepository.save(personal);
        return "redirect:/personal/list";
    }
     */

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String savePersonal(Personal persona, Model model){

        System.out.println("ID: " + persona.getId());
        personalRepository.save(persona);

        return "redirect:/personal/list";
    }
}
