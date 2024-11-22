package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.CotizaServicio;
import com.evoltech.ciqapm.model.Cotizacion;
import com.evoltech.ciqapm.model.Servicio;
import com.evoltech.ciqapm.repository.CotizacionRepository;
import com.evoltech.ciqapm.repository.CotizacionServiciosRepository;
import com.evoltech.ciqapm.repository.ServicioRepository;
import com.evoltech.ciqapm.utils.CotizaServiciosWrapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/cotiza")
public class CotizaServiciosController {

    @Autowired
    ServicioRepository servicioRepository;
    @Autowired
    CotizacionRepository cotizacionRepository;
    @Autowired
    CotizacionServiciosRepository cotizacionServiciosRepository;

    public CotizaServiciosController(ServicioRepository servicioRepository,
                                     CotizacionRepository cotizacionRepository,
                                     CotizacionServiciosRepository cotizacionServiciosRepository) {
        this.servicioRepository = servicioRepository;
        this.cotizacionRepository = cotizacionRepository;
        this.cotizacionServiciosRepository = cotizacionServiciosRepository;
    }

    @GetMapping("/list")
    private String listaCotizaServicio(@RequestParam("id") Long id, Model model){
        Cotizacion cotizacion = cotizacionRepository.getReferenceById(id);

        List<CotizaServicio> cotizacionServicios = cotizacionServiciosRepository.findByCotizacion(cotizacion);
        System.out.println("CotizaServicios:" + cotizacionServicios.size());
        model.addAttribute("cotizacionId", id);
        model.addAttribute("cotizaServicios", cotizacionServicios);
        return "/CotizaServicios/List";
    }

    @GetMapping("/new")
    private String nuevo(@RequestParam("id")Long id, Model model ){

        return "/CotizaServicio/Edit";
    }

    @GetMapping("/searchForm")
    private String SearchForm(@RequestParam("id") Long id, HttpSession session, Model model) {
        session.setAttribute("cotizacionId", id);
        return "CotizaServicios/Search";
    }

    @PostMapping("/search")
    public String lookup(String search, Model model) {
        System.out.println("Searching...");
        List<Servicio> servicios = servicioRepository.findByDescripcionContainsIgnoreCase(search);
        System.out.println("Servicios size:" + servicios.size());

        // servicios.stream().map(servicio -> servicio.getDescripcion()).forEach(System.out::println);
        model.addAttribute("servicios", servicios);

        return "CotizaServicios/Result :: result_table";
    }


    @GetMapping("/add")
    public String addServicio(@RequestParam("id") Long id, HttpSession session, Model model){
        Set<Servicio> set = new HashSet<>();

        List<Servicio> listaPrevia = (List<Servicio>)session.getAttribute("listaServicios");

        if(listaPrevia != null) {
            listaPrevia.stream().forEach(set::add);
        }

        Servicio servicio = servicioRepository.getReferenceById(id);

        if (servicio != null){
            set.add(servicio);
        }

        List<Servicio> lista = set.stream().toList();

        session.setAttribute("listaServicios",lista);
        model.addAttribute("servicios", lista);

        return "CotizaServicios/Result :: list";
    }

    @GetMapping("/getarray")
    public String getarray(HttpSession session, Model model){
        CotizaServiciosWrapper cotizaServiciosWrapper = new CotizaServiciosWrapper();

        List<Servicio> listaPrevia = (List<Servicio>) session.getAttribute("listaServicios");
        if(listaPrevia != null){
            listaPrevia.stream().forEach(p -> {
                CotizaServicio cs = new CotizaServicio();
                cs.setServicio(p);
                cs.setCantidad(1);
                cotizaServiciosWrapper.getServicios().add(cs);
            });
        }
        model.addAttribute("wrapper", cotizaServiciosWrapper);
        return "CotizaServicios/Summary";
    }

    @PostMapping("/postarray")
    public String postArray(CotizaServiciosWrapper serviciosWrapper,
                            HttpSession session, Model model){
        session.removeAttribute("listaServicios");
        Long id = (Long) session.getAttribute("cotizacionId");
        System.out.println("Cotizacion ID:" + id);
        Cotizacion cotizacion = cotizacionRepository.getReferenceById(id);

        List<CotizaServicio> cotizaServicios = serviciosWrapper.getServicios();

        cotizaServicios.stream().forEach(co -> co.setCotizacion(cotizacion));

        cotizaServicios.stream().map(CotizaServicio::getCotizacion).forEach(c -> System.out.println(c.getNombre()));

        cotizaServicios.stream().forEach(co -> cotizacionServiciosRepository.save(co));

        // TODO: salvar en la base de datos

        model.addAttribute("wrapper", serviciosWrapper);
        model.addAttribute("cotizacionId", id);
        return "redirect:/cotizacion/view/" + id;
    }

    @GetMapping("view")
    private String viewCotizaServicio(@RequestParam Long id, Model model){
        CotizaServicio cotizaServicio = cotizacionServiciosRepository.getReferenceById(id);

        model.addAttribute("cotizaServicio", cotizaServicio);

        return "CotizaServicios/View";
    }
}
