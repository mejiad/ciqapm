package com.evoltech.ciqapm.controllers.proyectos;

import com.evoltech.ciqapm.dto.GanttDTO;
import com.evoltech.ciqapm.dto.IndustriaDto;
import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.model.datos.DatosIndustria;
import com.evoltech.ciqapm.repository.*;
import com.evoltech.ciqapm.repository.datos.IndustriaRepository;
import com.evoltech.ciqapm.service.ProyectoServicio;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/industria")
public class IndustriaController {

    private static final Logger log = LogManager.getLogger(IndustriaController.class);

    @Autowired
    ProyectoServicio proyectoServicio;

    @Autowired
    ProyectoRepository proyectoRepository;

    @Autowired
    EtapaRepository etapaRepository;
    @Autowired
    private final EmpleadoRepository personalRepository;

    @Autowired
    private final ClienteRepository clienteRepository;

    @Autowired
    private final IndustriaRepository industriaRepository;

    public IndustriaController(ProyectoServicio proyectoServicio, ProyectoRepository proyectoRepository,
                               EtapaRepository etapaRepository,
                               EmpleadoRepository personalRepository,
                               IndustriaRepository industriaRepository,
                               ClienteRepository clienteRepository) {
        this.proyectoServicio = proyectoServicio;
        this.proyectoRepository = proyectoRepository;
        this.etapaRepository = etapaRepository;
        this.personalRepository = personalRepository;
        this.clienteRepository = clienteRepository;
        this.industriaRepository = industriaRepository;
    }

    @GetMapping("/list")
    public String listProyecto(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println("Usuario loggeado industria:" + username);

        List<Proyecto> proyectos = proyectoRepository.findByTipoProyecto(TipoProyecto.INDUSTRIA);

        model.addAttribute("proyectos", proyectos);

        return "Industria/List";
    }

    @GetMapping("/view/{id}")
    public String viewProyectoById(@PathVariable("id") Long id, Model model) {
        String pattern = "YYYY MM dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println("Nombre del usuario: " + username);

        Proyecto proyecto = proyectoRepository.getReferenceById(id);
        Industria industria = industriaRepository.getReferenceById(id);

        List<Etapa> etapas = etapaRepository.findByProyecto(industria);

        ArrayList<GanttDTO> ganttDTOS = new ArrayList<>();

        etapas.forEach(etapa -> {
            GanttDTO ganttDTO = new GanttDTO(etapa.getId().toString(),
                    etapa.getNombre(), etapa.getServicio().getClave(),
                    // LocalDate.of(2020,10,12).format(df),
                    etapa.getFechaEstimadaInicio().format(df),
                    etapa.getFechaEstimadaTerminacion().format(df) ,
                    10 , etapa.getPctCompleto() );
            ganttDTOS.add(ganttDTO);
        });


        model.addAttribute("proyecto", industria);
        model.addAttribute("etapas", ganttDTOS);

        return "Industria/View";
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
        Industria industria = industriaRepository.getReferenceById(id);

        List<Etapa> etapas = etapaRepository.findByProyecto(industria);

        ArrayList<GanttDTO> ganttDTOS = new ArrayList<>();

        etapas.forEach(etapa -> {
            GanttDTO ganttDTO = new GanttDTO(etapa.getId().toString(),
                    etapa.getNombre(), etapa.getServicio().getClave(),
                    // LocalDate.of(2020,10,12).format(df),
                    etapa.getFechaEstimadaInicio().format(df),
                    etapa.getFechaEstimadaTerminacion().format(df) ,
                    10 , etapa.getPctCompleto() );
            ganttDTOS.add(ganttDTO);
        });


        model.addAttribute("proyecto", industria);
        model.addAttribute("etapas", ganttDTOS);

        return "Industria/View";
    }

    @GetMapping("/edit")
    public String editProyecto(Model model) {
        return "Industria/Edit";
    }


    @GetMapping("/new")
    public String newIndustria(Model model) {
        Proyecto proyecto = new Proyecto();
        List<Estado> estados = List.of(Estado.values());
        List<Empleado> personas = personalRepository.findAll();
        List<Cliente> clientes = clienteRepository.findAll();
        // List<TipoProyecto> tiposProyecto = List.of(TipoProyecto.values());

        DatosIndustria industria = new DatosIndustria();
        proyecto.setTipoProyecto(TipoProyecto.INDUSTRIA);
        IndustriaDto industriaDto = new IndustriaDto();

        model.addAttribute("industriaDto", industriaDto);
        model.addAttribute("estados", estados);
        model.addAttribute("personas", personas);
        model.addAttribute("clientes", clientes);
        // model.addAttribute("tiposProyecto", tiposProyecto);
        model.addAttribute("industria", industria);

        return "Industria/Edit";
    }

    /*
    */



    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveProyecto(@Valid Industria industria, BindingResult result, Model model) {
        var mod = result.getModel();
        if(result.hasErrors()){
            System.out.println("++ Hay errores ++++++++++++++");
            if (!mod.isEmpty()) {
                mod.forEach((String k, Object obj) -> {
                    System.out.println("Error key: " + k + " Obj:" + obj);
                    System.out.println("++++++++++++++++++++++++");
                });
            }
            List<Estado> estados = List.of(Estado.values());
            List<Empleado> personas = personalRepository.findAll();
            List<Cliente> clientes = clienteRepository.findAll();
            //List<TipoProyecto> tiposProyecto = List.of(TipoProyecto.values());

            model.addAttribute("industriaDto", industria);
            model.addAttribute("estados", estados);
            model.addAttribute("personas", personas);
            model.addAttribute("clientes", clientes);
            //model.addAttribute("tiposProyecto", tiposProyecto);
            return "Industria/Edit";
        } else {
            System.out.println("Inicio del save");
            Industria proyecto = industria;
            proyecto.setEstatus(Estado.PROCESO);
            Industria res = industriaRepository.save(industria);

            new File("src/main/resources/directory/industria" + res.getId()).mkdirs();

            return "redirect:/industria/view?id=" + res.getId();
        }
    }

}
