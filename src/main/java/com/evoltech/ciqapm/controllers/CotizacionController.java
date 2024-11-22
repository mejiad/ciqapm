package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.Cotizacion;
import com.evoltech.ciqapm.model.Industria;
import com.evoltech.ciqapm.model.Proyecto;
import com.evoltech.ciqapm.repository.CotizacionEmpleadoRepository;
import com.evoltech.ciqapm.repository.CotizacionRepository;
import com.evoltech.ciqapm.repository.CotizacionServiciosRepository;
import com.evoltech.ciqapm.repository.ProyectoRepository;
import com.evoltech.ciqapm.repository.datos.IndustriaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cotizacion")
public class CotizacionController {

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private CotizacionEmpleadoRepository cotizacionEmpleadoRepository;

    @Autowired
    private CotizacionServiciosRepository cotizacionServiciosRepository;

    @Autowired
    private IndustriaRepository industriaRepository;

    public CotizacionController(CotizacionRepository cotizacionRepository, CotizacionEmpleadoRepository cotizacionEmpleadoRepository,
                                CotizacionServiciosRepository cotizacionServiciosRepository,
                                ProyectoRepository proyectoRepository) {
        this.cotizacionRepository = cotizacionRepository;
        this.cotizacionEmpleadoRepository = cotizacionEmpleadoRepository;
        this.cotizacionServiciosRepository = cotizacionServiciosRepository;
        this.industriaRepository = industriaRepository;
    }

    @GetMapping("listAll")
    private String listAll( Model model){
        List<Cotizacion> cotizaciones = cotizacionRepository.findAll();
        System.out.println("======== List de todas las cotizacion =========");

        model.addAttribute("cotizaciones", cotizaciones);
        return "/Cotizacion/ListAll";
    }

    @GetMapping("list")
    private String list(@RequestParam("id") Long id, Model model){
        Industria industria = industriaRepository.getReferenceById(id);
        List<Cotizacion> cotizaciones = cotizacionRepository.findByIndustria(industria);
        System.out.println("======== List de cotizacion =========");

        model.addAttribute("cotizaciones", cotizaciones);
        model.addAttribute("proyecto", industria);
        return "/Cotizacion/List";
    }

    @GetMapping("view/{id}")
    private String viewById(@PathVariable("id") Long id, Model model){
        Cotizacion cotizacion = cotizacionRepository.getReferenceById(id);
        Double subtotal = cotizacion.getPasajes() + cotizacion.getMateriales() +cotizacion.getViaticos() + cotizacion.getCostoTotalHrsHombre();
        model.addAttribute("cotizacion", cotizacion);
        model.addAttribute("subtotal", subtotal);
        return "/Cotizacion/View";
    }

    @GetMapping("view")
    private String view(@RequestParam("id") Long id, Model model){

        Cotizacion cotizacion = cotizacionRepository.getReferenceById(id);
        Double subtotal = cotizacion.getPasajes() + cotizacion.getMateriales() +cotizacion.getViaticos() + cotizacion.getCostoTotalHrsHombre();
        model.addAttribute("cotizacion", cotizacion);
        model.addAttribute("subtotal", subtotal);
        return "/Cotizacion/View";
    }

    @GetMapping("edit")
    private String edit(@RequestParam("id") Long id, Model model){
        Cotizacion cotizacion = cotizacionRepository.getReferenceById(id);

        model.addAttribute("cotizacion", cotizacion);
        return "/Cotizacion/Edit";
    }


    @GetMapping("new")
    private String nuevaCotizacion(@RequestParam("id") Long id,  Model model) {
        Industria industria = industriaRepository.getReferenceById(id);
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setIndustria(industria);

        model.addAttribute("cotizacion", cotizacion);
        return "Cotizacion/Edit";
    }


    @PostMapping("save")
    private String salvar(@Valid Cotizacion cotizacion, BindingResult result, Model model){

        if(result.hasErrors()){
            System.out.println("Errores en la captura de datos.");
            model.addAttribute("cotizacion", cotizacion);
            return "Cotizacion/Edit";
        } else {
            System.out.println("SIN Errores en la captura de datos.");
            cotizacionRepository.save(cotizacion);
        }
        return "redirect:/cotizacion/view?id=" + cotizacion.getId();
    }

}
