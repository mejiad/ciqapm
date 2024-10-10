package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.repository.PagoRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.evoltech.ciqapm.repository.ProyectoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("pago")
public class PagoController {

    private ProyectoRepository proyectoRepository;
    private PagoRepository pagoRepository;

    public PagoController(ProyectoRepository proyectoRepository, PagoRepository pagoRepository) {
        this.proyectoRepository = proyectoRepository;
        this.pagoRepository = pagoRepository;
    }

    @GetMapping("/list")
    public String listPago(@RequestParam("id") Long id, Model model) { // id del proyecto
        System.out.println("Numero de proyecto desde pago:" + id);
        Proyecto proyecto = proyectoRepository.getReferenceById(id);
        List<Pago> pagos = pagoRepository.findByProyecto(proyecto);
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("pagos", pagos);

        return "/Pagos/List";
    }


    @GetMapping("/view")
    public String viewPago(Model model){
        return "/Pagos/View";
    }

    @GetMapping("/edit")
    public String editPago(@RequestParam Long id, Model model){ // id del pago
        List<PagosEstado> estados = List.of(PagosEstado.values());
        Pago pago = pagoRepository.getReferenceById(id);
        Proyecto proyecto = pago.getProyecto();
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("pago", pago);
        model.addAttribute("estados", estados);
        return "/Pagos/Edit";
    }

    @GetMapping("/new")
    public String newEtapa(@RequestParam Long id,  Model model) {
        Proyecto proyecto = proyectoRepository.getReferenceById(id);
        List<PagosEstado> estados = List.of(PagosEstado.values());
        Pago pago = new Pago();
        pago.setProyecto(proyecto);
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("estados", estados);
        return "/Pagos/Edit";
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String savePago(Pago pago, Model model){
        System.out.println("Valor de pago proyecto: " + pago.getProyecto().getNombre());
        System.out.println("fechaFacturacion: " + pago.getFechaFacturacion());
        System.out.println("estado: " + pago.getEstado());
        pagoRepository.save(pago);
        return "redirect:/proyecto/list";
    }


}
