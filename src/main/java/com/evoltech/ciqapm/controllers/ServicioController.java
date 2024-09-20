package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/servicio")
public class ServicioController {
    @Autowired
    ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping("/list")
    public String list(Model model){
        return "/Servicio/list";
    }

    @GetMapping("/view")
    public String view(Model model){
        return "/Servicio/View";
    }

    @GetMapping("/edit")
    public String edit(Model model){
        return "/Servicio/Edit";
    }

    @PostMapping("/save")
    public String save(Model model){
        return "/Servicio/list";
    }


}
