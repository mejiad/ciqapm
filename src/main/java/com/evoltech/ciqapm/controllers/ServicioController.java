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
    public String listServicio(Model model){
        return "/Servicio/List";
    }

    @GetMapping("/view")
    public String viewServicio(Model model){
        return "/Servicio/View";
    }

    @GetMapping("/edit")
    public String editServicio(Model model){
        return "/Servicio/Edit";
    }

    @GetMapping("/new")
    public String newServicio(Model model){
        return "/Servicio/Edit";
    }

    @PostMapping("/save")
    public String saveServicio(Model model){
        return "/Servicio/List";
    }


}
