package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.repository.*;
import com.evoltech.ciqapm.utils.CotizaEmpleadoWrapper;
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
@RequestMapping("cotizaPersonal")
public class CotizaEmpleadoController {

    @Autowired
    EmpleadoRepository empleadoRepository;
    @Autowired
    CotizacionRepository cotizacionRepository;
    @Autowired
    CotizacionEmpleadoRepository cotizacionEmpleadoRepository;

    public CotizaEmpleadoController(EmpleadoRepository empleadoRepository,
                                    CotizacionRepository cotizacionRepository,
                                    CotizacionEmpleadoRepository cotizacionEmpleadoRepository) {
        this.empleadoRepository = empleadoRepository;
        this.cotizacionRepository = cotizacionRepository;
        this.cotizacionEmpleadoRepository = cotizacionEmpleadoRepository;
    }

    @GetMapping("/list")
    private String listaCotizaServicio(@RequestParam("id") Long id, Model model){
        Cotizacion cotizacion = cotizacionRepository.getReferenceById(id);

        List<CotizaEmpleado> cotizaEmpleados = cotizacionEmpleadoRepository.findByCotizacion(cotizacion);
        System.out.println("CotizaEmpleados:" + cotizaEmpleados.size());
        model.addAttribute("cotizacionId", id);
        model.addAttribute("cotizaEmpleados", cotizaEmpleados);
        return "CotizaPersonal/List";
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
        List<Empleado> personal = empleadoRepository.findByNombreContainsIgnoreCase(search);
        System.out.println("Servicios size:" + personal.size());

        // servicios.stream().map(servicio -> servicio.getDescripcion()).forEach(System.out::println);
        model.addAttribute("personal", personal);

        return "CotizaPersonal/Result :: result_table";
    }

    @GetMapping("/add")
    public String addEmpleado(@RequestParam("id") Long id, HttpSession session, Model model){
        Set<Empleado> set = new HashSet<>();

        List<Empleado> listaPrevia = (List<Empleado>)session.getAttribute("lista");
        if(listaPrevia != null) {
            listaPrevia.stream().forEach(set::add);
        }

        Empleado personal = empleadoRepository.getReferenceById(id);

        if (personal != null){
            set.add(personal);
        }
        List<Empleado> lista = set.stream().toList();

        session.setAttribute("lista",lista);
        model.addAttribute("personal", lista);

        return "CotizaPersonal/Result :: list";
    }

    @GetMapping("/getarray")
    public String getarray(HttpSession session, Model model){
        CotizaEmpleadoWrapper cotizaEmpleadoWrapper = new CotizaEmpleadoWrapper();

        List<Empleado> listaPrevia = (List<Empleado>) session.getAttribute("lista");
        if(listaPrevia != null){
            listaPrevia.stream().forEach(p -> {
                CotizaEmpleado cs = new CotizaEmpleado();
                cs.setEmpleado(p);
                cs.setHoras(1);
                cotizaEmpleadoWrapper.getEmpleados().add(cs);
            });
        }
        model.addAttribute("wrapper", cotizaEmpleadoWrapper);
        return "CotizaPersonal/Summary";
    }

    @PostMapping("/postarray")
    public String postArray(CotizaEmpleadoWrapper personalWrapper,
                            HttpSession session, Model model){
        session.removeAttribute("lista");
        Long id = (Long) session.getAttribute("cotizacionId");
        System.out.println("Cotizacion ID:" + id);
        Cotizacion cotizacion = cotizacionRepository.getReferenceById(id);

        List<CotizaEmpleado> cotizaEmpleados = personalWrapper.getEmpleados();

        cotizaEmpleados.stream().forEach(co -> co.setCotizacion(cotizacion));

        cotizaEmpleados.stream().map(CotizaEmpleado::getCotizacion).forEach(c -> System.out.println(c.getNombre()));

        cotizaEmpleados.stream().forEach(co -> cotizacionEmpleadoRepository.save(co));


        model.addAttribute("wrapper", personalWrapper);
        model.addAttribute("cotizacionId", id);
        return "redirect:/cotizacion/view/" + id;
    }

    @GetMapping("view")
    private String viewCotizaEmpleado(@RequestParam Long id, Model model){
        CotizaEmpleado cotizaEmpleado = cotizacionEmpleadoRepository.getReferenceById(id);

        model.addAttribute("cotizaEmpleado", cotizaEmpleado);

        return "CotizaPersonal/View";
    }
}
