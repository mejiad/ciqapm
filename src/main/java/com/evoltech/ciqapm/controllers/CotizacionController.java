package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.Cotizacion;
import com.evoltech.ciqapm.repository.CotizacionPersonalRepository;
import com.evoltech.ciqapm.repository.CotizacionRepository;
import com.evoltech.ciqapm.repository.CotizacionServiciosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/cotizacion")
public class CotizacionController {

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private CotizacionPersonalRepository cotizacionPersonalRepository;

    @Autowired
    private CotizacionServiciosRepository cotizacionServiciosRepository;

    public CotizacionController(CotizacionRepository cotizacionRepository, CotizacionPersonalRepository cotizacionPersonalRepository, CotizacionServiciosRepository cotizacionServiciosRepository) {
        this.cotizacionRepository = cotizacionRepository;
        this.cotizacionPersonalRepository = cotizacionPersonalRepository;
        this.cotizacionServiciosRepository = cotizacionServiciosRepository;
    }

    @GetMapping("list")
    private String list(Model model){

        List<Cotizacion> cotizaciones = cotizacionRepository.findAll();

        model.addAttribute("cotizaciones", cotizaciones);
        return "/Cotizacion/List";
    }

    @GetMapping("view")
    private String view(@RequestParam("id") Long id, Model model){

        Cotizacion cotizacion = cotizacionRepository.getReferenceById(id);
        model.addAttribute("cotizacion", cotizacion);
        return "/Cotizacion/View";
    }

    @GetMapping("edit")
    private String edit(@RequestParam("id") Long id, Model model){
        Cotizacion cotizacion = cotizacionRepository.getReferenceById(id);

        model.addAttribute("cotizacion", cotizacion);
        return "/Cotizacion/Edit";
    }

}
