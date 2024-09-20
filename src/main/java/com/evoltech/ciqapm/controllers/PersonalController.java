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
    public String list(Model model){
        return "/Personal/list";
    }

    @GetMapping("/view")
    public String view(Model model){
        return "/Personal/View";
    }

    @GetMapping("/edit")
    public String edit(Model model){
        return "/Personal/Edit";
    }

    @PostMapping("/save")
    public String save(Model model){
        return "/Personal/list";
    }


}
