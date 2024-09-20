package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.service.EtapaService;
import com.evoltech.ciqapm.service.PersonalServicio;
import com.evoltech.ciqapm.service.ProyectoServicio;
import com.evoltech.ciqapm.service.ServicioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.*;

@Slf4j
@Controller
public class MainController {


    @Autowired
    ServicioService servicioService;

    @Autowired
    PersonalServicio personalServicio;

    @Autowired
    EtapaService etapaService;

    @Autowired
    ProyectoServicio proyectoServicio;


    public MainController(ServicioService servicioService, PersonalServicio personalServicio,
                          EtapaService etapaService,
                          ProyectoServicio proyectoServicio){
        this.servicioService = servicioService;
        this.personalServicio = personalServicio;
        this.etapaService = etapaService;
        this.proyectoServicio = proyectoServicio;
    }

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/add_01")
    @ResponseBody
    public String add_01(Model model){
        Servicio servicio = new Servicio();
        servicio.setId(0L);
        servicio.setNombre("Primer servicio");
        servicio.setDescripcion("Decripcion primer servicio");
        servicio.setCosto(new BigDecimal("100.9599"));
        servicio.setEntregableEsperado("Reporte de etapa");
        servicio.setHorasPromedioRealizacion(100);
        servicioService.save(servicio);

        return "Servicio salvado";
    }

    @GetMapping("/add_02")
    @ResponseBody
    public String add_02(Model model) {
        Personal personal = new Personal();

        personal.setId(0L);
        personal.setNombre("Nombre del Empleado 1");
        personal.setApellidos("Apellidos empleado 1");
        personal.setCategoria(PersonalCategoria.ITA);
        model.addAttribute("Attr", "Valor de attr 1");


        Personal personal1 = personalServicio.addPersonal(personal);

        return "Personal saved";
    }

    @GetMapping("/add_03")
    @ResponseBody
    public String add_03(Model model){
        Personal personal = new Personal();

        personal.setId(0L);
        personal.setNombre("Nombre del Empleado 1");
        personal.setApellidos("Apellidos empleado 1");
        personal.setCategoria(PersonalCategoria.ITA);
        model.addAttribute("Attr", "Valor de attr 1");

        Personal personal1 = personalServicio.addPersonal(personal);

        Etapa etapa = new Etapa();
        etapa.setId(0L);
        etapa.setNombre("Etapa 1");
        etapa.setDescripcion("Etapa 1");
        etapa.setEntregable("El entregabñe de etapa 1 es un paletón");
        etapa.setFechaEstimadaTerminacion(new Date());
        etapa.setResponsable(personal1);

        Etapa newEtapa = etapaService.save(etapa);

        return "Etapa salvada: " + newEtapa.getId();
    }

    @GetMapping("/add_04")
    @ResponseBody
    public String add_o4(Model model){


        Proyecto proyecto = creaProyecto();

        return "Proyecto salvado: " + proyecto.getNombre();
    }



    @GetMapping("get_01")
    @ResponseBody
    public String get_01(@RequestParam("id") Long id, Model model){
        String ret = "";
        creaPersonal();
        try {
            Personal personal = personalServicio.findById(id);
            System.out.println("+++ " + personal.getCategoria());
            ret = personal.getCategoria().toString();
        } catch (Exception e) {
            log.error("No existe el id:" + id);
            ret = "No existe el id:" + id;
        }
        return ret;
    }

    private Etapa creaEtapa(){

        Personal personal1 = creaPersonal();

        Etapa etapa = new Etapa();
        etapa.setId(0L);
        etapa.setNombre("Etapa 1");
        etapa.setDescripcion("Etapa 1");
        etapa.setEntregable("El entregable de etapa 1 es un paletón");
        etapa.setFechaEstimadaTerminacion(new Date());
        etapa.setResponsable(personal1);

        Etapa newEtapa = etapaService.save(etapa);

        return newEtapa;
    }

    private Servicio creaServicio(){
        Servicio servicio = new Servicio();
        servicio.setId(null);
        servicio.setNombre("Primer servicio");
        servicio.setDescripcion("Ir por las cocas");
        servicio.setEntregableEsperado("Dos botellas de vidrio de cocas");
        servicio.setCosto(new BigDecimal("123.34"));

        Servicio res = servicioService.save(servicio);
        return  res;
    }

    private Personal creaPersonal() {
        java.util.Currency rate;
        java.util.Currency currency = java.util.Currency.getInstance("MXN");
        Personal personal = new Personal();
        personal.setId(0L);
        personal.setNombre("Nombre del Empleado 1");
        personal.setApellidos("Apellidos empleado 1");
        personal.setCategoria(PersonalCategoria.ITA);
        personal.setRate(new BigDecimal(10.20));

        Personal personal1 = personalServicio.addPersonal(personal);
        return personal1;
    }

    private Proyecto creaProyecto() {

        Proyecto proyecto = new Proyecto();
        proyecto.setId(0L);
        proyecto.setNombre("Proyecto de prueba");
        proyecto.setDescripcion("Descripción del proyecto");
        proyecto.setResponsable(creaPersonal());
        proyecto.setTipoProyecto(TipoProyecto.INDUSTRIA);
        proyecto.setEtapas(List.of(creaEtapa(), creaEtapa()));

        Proyecto newProyecto = proyectoServicio.save(proyecto);

        return newProyecto;
    }

}
