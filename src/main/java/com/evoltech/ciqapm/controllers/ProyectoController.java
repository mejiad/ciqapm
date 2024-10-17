package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.dto.GanttDTO;
import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.model.datos.DatosConahcyt;
import com.evoltech.ciqapm.repository.*;
import com.evoltech.ciqapm.repository.datos.ConahcytRepository;
import com.evoltech.ciqapm.service.ProyectoServicio;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.HateoasAwareSpringDataWebConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/proyecto")
public class ProyectoController {

    private static final Logger log = LogManager.getLogger(ProyectoController.class);

    @Autowired
    ProyectoServicio proyectoServicio;

    @Autowired
    ProyectoRepository proyectoRepository;

    @Autowired
    EtapaRepository etapaRepository;
    @Autowired
    private final PersonalRepository personalRepository;
    @Autowired
    private final ClienteRepository clienteRepository;

    @Autowired
    private final ConahcytRepository conahcytRepository;

    public ProyectoController(ProyectoServicio proyectoServicio, ProyectoRepository proyectoRepository,
                              EtapaRepository etapaRepository,
                              PersonalRepository personalRepository,
                              ConahcytRepository conahcytRepository,
                              ClienteRepository clienteRepository
                              ) {
        this.proyectoServicio = proyectoServicio;
        this.proyectoRepository = proyectoRepository;
        this.etapaRepository = etapaRepository;
        this.personalRepository = personalRepository;
        this.clienteRepository = clienteRepository;
        this.conahcytRepository = conahcytRepository;
    }

    @GetMapping("/list")
    public String listProyecto(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println("Usuario loggeado:" + username);

        List<Proyecto> proyectos = proyectoRepository.findAll();

        model.addAttribute("proyectos", proyectos);

        return "/Proyecto/List";
    }

    @GetMapping("/view")
    public String viewProyecto(@RequestParam("id") Long id, Model model) {
        String pattern = "YYYY MM dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println("Nombre del usuario: " + username);

        Proyecto proyecto = proyectoRepository.getReferenceById(id);
        List<Etapa> etapas = etapaRepository.findByProyecto(proyecto);

        ArrayList<GanttDTO> ganttDTOS = new ArrayList<>();

        etapas.forEach(etapa -> {
            GanttDTO ganttDTO = new GanttDTO(etapa.getId().toString(),
                    etapa.getNombre(), etapa.getServicio().getNombre(),
                    // LocalDate.of(2020,10,12).format(df),
                    etapa.getFechaEstimadaInicio().format(df),
                    etapa.getFechaEstimadaTerminacion().format(df) ,
                    10 , etapa.getPctCompleto() );
            ganttDTOS.add(ganttDTO);
        });

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("etapas", ganttDTOS);

        return "/Proyecto/View";
    }

    @GetMapping("/edit")
    public String editProyecto(Model model) {
        return "/Proyecto/Edit";
    }

    @GetMapping("/new")
    public String newProyecto(Model model) {
        Proyecto proyecto = new Proyecto();
        List<Estado> estados = List.of(Estado.values());
        List<Personal> personas = personalRepository.findAll();
        List<Cliente> clientes = clienteRepository.findAll();
        List<TipoProyecto> tiposProyecto = List.of(TipoProyecto.values());

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("estados", estados);
        model.addAttribute("personas", personas);
        model.addAttribute("clientes", clientes);
        model.addAttribute("tiposProyecto", tiposProyecto);

        return "/Proyecto/Edit";
    }

    @GetMapping("/newConahcyt")
    public String newConahcyt(Model model) {
        Proyecto proyecto = new Proyecto();
        List<Estado> estados = List.of(Estado.values());
        List<Personal> personas = personalRepository.findAll();
        List<Cliente> clientes = clienteRepository.findAll();
        List<TipoProyecto> tiposProyecto = List.of(TipoProyecto.values());
        DatosConahcyt conahcyt = new DatosConahcyt();
        proyecto.setTipoProyecto(TipoProyecto.CONAHCYT);

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("estados", estados);
        model.addAttribute("personas", personas);
        model.addAttribute("clientes", clientes);
        model.addAttribute("tiposProyecto", tiposProyecto);
        model.addAttribute("conahcyt", conahcyt);

        return "/Proyecto/EditConahcyt";
    }

    /*
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveDocumento(Documento documento, Model model){
    */

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveProyecto(@Valid Proyecto proyecto, BindingResult result, Model model) {

        System.out.println("Creación de proyecto");
        if(result.hasErrors()){
            System.out.println("=== Hay errores con los datos ===");
            var mod = result.getModel();
            System.out.println("Numero de errores globales:" + result.getGlobalErrorCount());
            System.out.println("Mpod size:" + mod.isEmpty());
            if (!mod.isEmpty()) {
                mod.forEach((String k, Object obj) -> {
                    System.out.println("Error key: " + k + " Obj:" + obj);
                    System.out.println("++++++++++++++++++++++++");
                });
            }

            System.out.println("Nombre:" + proyecto.getNombre());

            List<Estado> estados = List.of(Estado.values());
            List<Personal> personas = personalRepository.findAll();
            List<Cliente> clientes = clienteRepository.findAll();
            List<TipoProyecto> tiposProyecto = List.of(TipoProyecto.values());

            model.addAttribute("proyecto", proyecto);
            model.addAttribute("estados", estados);
            model.addAttribute("personas", personas);
            model.addAttribute("clientes", clientes);
            model.addAttribute("tiposProyecto", tiposProyecto);

            return "Proyecto/Edit";
        }
        else {
            System.out.println("+++ NO Hay errores +++:" + proyecto.getNombre());
            model.addAttribute("proyecto", proyecto);
            return "Proyecto/Edit";
        }

        // Proyecto res = proyectoRepository.save(proyecto);

        // System.out.println("ID del nuevo proyecto: " + res.getId());
        // new File("src/main/resources/directory/" + res.getId()).mkdirs();
        // return "redirect:/proyecto/list";
    }

    @PostMapping(value = "/saveConahcyt", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveProyecto(Proyecto proyecto, DatosConahcyt conahcyt, Model model) {

        System.out.println("convocatoria Conacyt: " + conahcyt.getConvocatoria());

        // TODO: Crear el directorio del id del proyecto

        proyecto.setTipoProyecto(TipoProyecto.CONAHCYT);
        Proyecto res = proyectoRepository.save(proyecto);
        conahcyt.setProyecto(res);
        conahcytRepository.save(conahcyt);

        // res = proyectoRepository.save(res);

        System.out.println("ID del nuevo proyecto: " + res.getId());
        new File("src/main/resources/directory/" + res.getId()).mkdirs();
        return "redirect:/proyecto/list";
    }

    public int avance(Long id){
         // return proyectoServicio.calculaAvance(id);
        return 20;
    }
}
