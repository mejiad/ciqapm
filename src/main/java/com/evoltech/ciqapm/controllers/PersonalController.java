package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.service.PersonalServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("personal")
public class PersonalController {
    @Autowired
    PersonalServicio personalServicio;

    public PersonalController(PersonalServicio personalServicio) {
        this.personalServicio = personalServicio;
    }


    @GetMapping("/list")
    public String listPersonal(Model model){
        return "/Personal/List";
    }

    @GetMapping("/view")
    public String viewPersonal(Model model){
        return "/Personal/View";
    }

    @GetMapping("/edit")
    public String editPersonal(Model model){
        return "/Personal/Edit";
    }

    @GetMapping("/new")
    public String newPersonal(Model model){
        return "/Personal/Edit";
    }

    @PostMapping("/save")
    public String savePersonal(Model model){
        return "/Personal/List";
    }


}
