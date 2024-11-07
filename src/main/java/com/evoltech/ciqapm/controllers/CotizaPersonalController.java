package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.repository.*;
import com.evoltech.ciqapm.utils.CotizaPersonalWrapper;
import com.evoltech.ciqapm.utils.CotizaServiciosWrapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("cotizaPersonal")
public class CotizaPersonalController {

    @Autowired
    PersonalRepository personalRepository;
    @Autowired
    CotizacionRepository cotizacionRepository;
    @Autowired
    CotizacionPersonalRepository cotizacionPersonalRepository;

    public CotizaPersonalController(PersonalRepository personalRepository,
                                    CotizacionRepository cotizacionRepository,
                                    CotizacionPersonalRepository cotizacionPersonalRepository) {
        this.personalRepository = personalRepository;
        this.cotizacionRepository = cotizacionRepository;
        this.cotizacionPersonalRepository = cotizacionPersonalRepository;
    }

    @GetMapping("/list")
    private String listaCotizaServicio(@RequestParam("id") Long id, Model model){
        Cotizacion cotizacion = cotizacionRepository.getReferenceById(id);

        List<CotizaPersonal> cotizacionPersonal = cotizacionPersonalRepository.findByCotizacion(cotizacion);
        System.out.println("CotizaPersonal:" + cotizacionPersonal.size());
        model.addAttribute("cotizacionId", id);
        model.addAttribute("cotizaPersonal", cotizacionPersonal);
        return "/CotizaPersonal/List";
    }

    @GetMapping("/new")
    private String nuevo(@RequestParam("id")Long id, Model model ){

        return "/CotizaPersonal/Edit";
    }

    @GetMapping("/searchForm")
    private String SearchForm(@RequestParam("id") Long id, HttpSession session, Model model) {
        session.setAttribute("cotizacionId", id);
        return "CotizaPersonal/Search";
    }

    @PostMapping("/search")
    public String lookup(String search, Model model) {
        System.out.println("Searching...");
        List<Personal> personal = personalRepository.findByNombreContainsIgnoreCase(search);
        System.out.println("Servicios size:" + personal.size());

        // servicios.stream().map(servicio -> servicio.getDescripcion()).forEach(System.out::println);
        model.addAttribute("personal", personal);

        return "CotizaPersonal/Result :: result_table";
    }


    @GetMapping("/add")
    public String addPersonal(@RequestParam("id") Long id, HttpSession session, Model model){
        List<Personal> lista = new ArrayList<>();

        List<Personal> listaPrevia = (List<Personal>)session.getAttribute("lista");
        if(listaPrevia != null) {
            listaPrevia.stream().forEach(lista::add);
        }

        Personal personal = personalRepository.getReferenceById(id);

        if (personal != null){
            lista.add(personal);
        }

        session.setAttribute("lista",lista);

        model.addAttribute("personal", lista);

        return "CotizaPersonal/Result :: list";
    }

    @GetMapping("/getarray")
    public String getarray(HttpSession session, Model model){
        CotizaPersonalWrapper cotizaPersonalWrapper = new CotizaPersonalWrapper();

        List<Personal> listaPrevia = (List<Personal>) session.getAttribute("lista");
        if(listaPrevia != null){
            listaPrevia.stream().forEach(p -> {
                CotizaPersonal cs = new CotizaPersonal();
                cs.setPersonal(p);
                cs.setHoras(1);
                cotizaPersonalWrapper.getPersonal().add(cs);
            });
        }
        model.addAttribute("wrapper", cotizaPersonalWrapper);
        return "CotizaPersonal/Summary";
    }

    @PostMapping("/postarray")
    public String postArray(CotizaPersonalWrapper personalWrapper,
                            HttpSession session, Model model){
        session.removeAttribute("lista");
        Long id = (Long) session.getAttribute("cotizacionId");
        System.out.println("Cotizacion ID:" + id);
        Cotizacion cotizacion = cotizacionRepository.getReferenceById(id);

        List<CotizaPersonal> cotizaPersonal = personalWrapper.getPersonal();

        cotizaPersonal.stream().forEach(co -> co.setCotizacion(cotizacion));

        cotizaPersonal.stream().map(CotizaPersonal::getCotizacion).forEach(c -> System.out.println(c.getNombre()));

        cotizaPersonal.stream().forEach(co -> cotizacionPersonalRepository.save(co));


        model.addAttribute("wrapper", personalWrapper);
        model.addAttribute("cotizacionId", id);
        return "redirect:/cotizacion/view/" + id;
    }

    @GetMapping("view")
    private String viewCotizaPersonal(@RequestParam Long id, Model model){
        CotizaPersonal cotizaPersonal = cotizacionPersonalRepository.getReferenceById(id);

        model.addAttribute("cotizaPersonal", cotizaPersonal);

        return "CotizaPersonal/View";
    }
}
