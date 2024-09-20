package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.service.EtapaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/etapa")
public class EtapaController {

    @Autowired
    EtapaService etapaService;

    public EtapaController(EtapaService etapaService) {
        this.etapaService = etapaService;
    }

    @GetMapping("/list")
    public String listEtapa(Model model){
        return "/Etapa/List";
    }

    @GetMapping("/view")
    public String viewEtapa(Model model){
        return "/Etapa/View";
    }

    @GetMapping("/edit")
    public String editEtapa(Model model){
        return "/Etapa/Edit";
    }

    @GetMapping("/new")
    public String newEtapa(Model model) {
        return "/Etapa/Edit";
    }
        @PostMapping("/save")
    public String saveEtapa(Model model){
        return "/Etapa/List";
    }

}
