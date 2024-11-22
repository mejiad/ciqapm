package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.repository.EtapaRepository;
import com.evoltech.ciqapm.repository.EtapaServicioRepository;
import com.evoltech.ciqapm.repository.ServicioRepository;
import com.evoltech.ciqapm.utils.CotizaServiciosWrapper;
import com.evoltech.ciqapm.utils.EtapaServicioWrapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("etapaservicio")
public class EtapaServicioController {

    @Autowired
    EtapaServicioRepository etapaServicioRepository;
    @Autowired
    EtapaRepository etapaRepository;
    @Autowired
    ServicioRepository servicioRepository;

    public EtapaServicioController(EtapaServicioRepository etapaServicioRepository, EtapaRepository etapaRepository, ServicioRepository servicioRepository) {
        this.etapaServicioRepository = etapaServicioRepository;
        this.etapaRepository = etapaRepository;
        this.servicioRepository = servicioRepository;
    }

    @GetMapping("list")
    public String lista(@RequestParam("id") Long id, Model model) {
        Etapa etapa = etapaRepository.getReferenceById(id);
        List<EtapaServicio> etapaServicios = etapaServicioRepository.findByEtapa(etapa);
        model.addAttribute("etapa", etapa);
        model.addAttribute("etapaServicios", etapaServicios);
        return "EtapaServicio/List";
    }

    @GetMapping("/searchForm")
    private String SearchForm(@RequestParam("id") Long id, HttpSession session, Model model) {
        session.setAttribute("etapaId", id);
        Etapa etapa = etapaRepository.getReferenceById(id);
        System.out.println("Etapa ID: " + etapa.getId());

        return "EtapaServicio/Search";
    }

    @PostMapping("/search")
    public String lookup(String search, Model model) {
        System.out.println("Searching...");
        List<Servicio> servicios = servicioRepository.findByDescripcionContainsIgnoreCase(search);
        System.out.println("Servicios size:" + servicios.size());

        // servicios.stream().map(servicio -> servicio.getDescripcion()).forEach(System.out::println);
        model.addAttribute("servicios", servicios);

        return "EtapaServicio/Result :: result_table";
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

        return "EtapaServicio/Result :: list";
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
        return "EtapaServicio/Summary";
    }

    @PostMapping("/postarray")
    public String postArray(EtapaServicioWrapper serviciosWrapper,
                            HttpSession session, Model model){
        session.removeAttribute("listaServicios");
        Long id = (Long) session.getAttribute("etapaId");
        System.out.println("Etapa ID:" + id);
        Etapa  etapa = etapaRepository.getReferenceById(id);

        List<EtapaServicio> etapaServicios = serviciosWrapper.getServicios();

        etapaServicios.stream().forEach(co -> co.setEtapa(etapa));

        etapaServicios.stream().map(EtapaServicio::getEtapa).forEach(c -> System.out.println(c.getNombre()));

        etapaServicios.stream().forEach(co -> etapaServicioRepository.save(co));


        model.addAttribute("wrapper", serviciosWrapper);
        model.addAttribute("etapaId", id);
        return "redirect:/etapa/view/" + id;
    }

    @GetMapping("view")
    private String viewCotizaServicio(@RequestParam Long id, Model model){
        EtapaServicio etapaServicio = etapaServicioRepository.getReferenceById(id);

        model.addAttribute("etapaServicio", etapaServicio);

        return "EtapaServicio/View";
    }

}
