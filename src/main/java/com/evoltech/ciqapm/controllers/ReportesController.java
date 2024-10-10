package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.dto.reportes.proyecto.ReporteProyectoDto;
import com.evoltech.ciqapm.model.Etapa;
import com.evoltech.ciqapm.model.Proyecto;
import com.evoltech.ciqapm.repository.EtapaRepository;
import com.evoltech.ciqapm.repository.ProyectoRepository;
import com.evoltech.ciqapm.service.ReportesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("reports")
public class ReportesController {

    ReportesService reportesService;
    EtapaRepository etapaRepository;



    public ReportesController(ReportesService reportesService) {
        this.reportesService = reportesService;

    }

    @GetMapping("")
    public String index(){
        return "/Reportes/index";
    }

    @GetMapping("proyectosSummary")
    public String proyectosSummary(Model model){

        List<ReporteProyectoDto> lista = reportesService.proyectos();
        model.addAttribute("proyectos", lista);

        return "/Reportes/proyectosSummary";
    }

    @GetMapping("proyectosEtapas")
    public String etapasProyectos(Model model){
        List<ReporteProyectoDto> dtos = reportesService.proyectosEtapas();
        model.addAttribute("proyectos", dtos);
        return "/Reportes/proyectosEtapas";
    }


}
