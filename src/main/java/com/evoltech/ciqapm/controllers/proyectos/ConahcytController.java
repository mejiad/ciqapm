package com.evoltech.ciqapm.controllers.proyectos;

import com.evoltech.ciqapm.dto.ConahcytDto;
import com.evoltech.ciqapm.dto.GanttDTO;
import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.model.jpa.Convocatoria;
import com.evoltech.ciqapm.repository.*;
import com.evoltech.ciqapm.repository.datos.ConahcytRepository;
import com.evoltech.ciqapm.service.ConahcytService;
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
@RequestMapping("/conahcyt")
public class ConahcytController {

    private static final Logger log = LogManager.getLogger(ConahcytController.class);

    @Autowired
    ProyectoServicio proyectoServicio;

    // @Autowired
    // ProyectoRepository proyectoRepository;

    @Autowired
    EtapaRepository etapaRepository;

    @Autowired
    private final EmpleadoRepository personalRepository;

    @Autowired
    private final ConahcytRepository conahcytRepository;

    @Autowired
    private final ConvocatoriaRepository convocatoriaRepository;

    @Autowired
    private final ConahcytService conahcytService;

    public ConahcytController(EmpleadoRepository personalRepository, ConahcytRepository conahcytRepository, ConvocatoriaRepository convocatoriaRepository, ConahcytService conahcytService, EtapaRepository etapaRepository, ProyectoRepository proyectoRepository, ProyectoServicio proyectoServicio) {
        this.personalRepository = personalRepository;
        this.conahcytRepository = conahcytRepository;
        this.convocatoriaRepository = convocatoriaRepository;
        this.conahcytService = conahcytService;
        this.etapaRepository = etapaRepository;
        // this.proyectoRepository = proyectoRepository;
        this.proyectoServicio = proyectoServicio;
    }

    @GetMapping("/list")
    public String listProyecto(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        List<ConahcytDto> dtos = conahcytService.findAll();
        model.addAttribute("proyectos", dtos);

        return "Conahcyt/List";
    }

    @GetMapping("/{id}/view")
    public String viewIdProyecto(@PathVariable("id") Long id,  @RequestParam("tab") String tab,
                                 Model model) {
        String pattern = "YYYY MM dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        System.out.println("EL VALOR DE TAB:" + tab);

        Conahcyt proyecto = conahcytRepository.getReferenceById(id);
        List<Etapa> etapas = etapaRepository.findByProyecto(proyecto);
        Conahcyt conahcyt = conahcytRepository.getReferenceById(id);
        int avance = proyecto.getAvance();

        ArrayList<GanttDTO> ganttDTOS = new ArrayList<>();

        etapas.forEach(etapa -> {
            GanttDTO ganttDTO = new GanttDTO(etapa.getId().toString(),
                    etapa.getNombre(), etapa.getNombre(),
                    etapa.getFechaEstimadaInicio().format(df),
                    etapa.getFechaEstimadaTerminacion().format(df) ,
                    10 , etapa.getPctCompleto() );
            ganttDTOS.add(ganttDTO);
        });

        ConahcytDto conahcytDto =  conahcytService.createDto(conahcyt);

        model.addAttribute("conahcytDto", conahcytDto);
        model.addAttribute("etapas", ganttDTOS);
        model.addAttribute("avance", avance);
        model.addAttribute("tab", tab);

        return "Conahcyt/View";
    }

    @GetMapping("/view/{id}")
    public String viewProyectoId(@PathVariable("id") Long id, Model model) {
        String pattern = "YYYY MM dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Conahcyt proyecto = conahcytRepository.getReferenceById(id);
        List<Etapa> etapas = etapaRepository.findByProyecto(proyecto);
        Conahcyt conahcyt = conahcytRepository.getReferenceById(id);
        int avance = proyecto.getAvance();

        ArrayList<GanttDTO> ganttDTOS = new ArrayList<>();

        etapas.forEach(etapa -> {
            GanttDTO ganttDTO = new GanttDTO(etapa.getId().toString(),
                    etapa.getNombre(), etapa.getNombre(),
                    etapa.getFechaEstimadaInicio().format(df),
                    etapa.getFechaEstimadaTerminacion().format(df) ,
                    10 , etapa.getPctCompleto() );
            ganttDTOS.add(ganttDTO);
        });

       ConahcytDto conahcytDto =  conahcytService.createDto(conahcyt);

        model.addAttribute("conahcytDto", conahcytDto);
        model.addAttribute("etapas", ganttDTOS);
        model.addAttribute("avance", avance);

        return "Conahcyt/View";
    }


    @GetMapping("/view")
    public String viewProyecto(@RequestParam("id") Long id, Model model) {
        System.out.println("Entrando a proyecto view");
        String pattern = "YYYY MM dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Proyecto proyecto = proyectoRepository.getReferenceById(id);
        Conahcyt conahcyt = conahcytRepository.getReferenceById(id);
        List<Etapa> etapas = etapaRepository.findByProyecto(conahcyt);
        int avance = conahcyt.getAvance();

        ArrayList<GanttDTO> ganttDTOS = new ArrayList<>();

        etapas.forEach(etapa -> {
            GanttDTO ganttDTO = new GanttDTO(etapa.getId().toString(),
                    etapa.getNombre(), etapa.getNombre(),
                    etapa.getFechaEstimadaInicio().format(df),
                    etapa.getFechaEstimadaTerminacion().format(df) ,
                    10 , etapa.getPctCompleto() );
            ganttDTOS.add(ganttDTO);
        });

        model.addAttribute("conahcytDto", conahcyt);
        model.addAttribute("etapas", ganttDTOS);
        model.addAttribute("avance", avance);

        return "Conahcyt/View";
    }

    @GetMapping("/edit")
    public String editProyecto(Model model) {
        return "Proyecto/Edit";
    }


    @GetMapping("/new")
    public String newConahcyt(Model model) {
        ConahcytDto proyecto = new ConahcytDto();
        List<Estado> estados = List.of(Estado.values());
        List<Empleado> personas = personalRepository.findAll();
        List<Convocatoria> convocatorias = convocatoriaRepository.findAll();

        proyecto.setEstatus(Estado.CREACION);

        model.addAttribute("conahcyt", proyecto);
        model.addAttribute("estados", estados);
        model.addAttribute("personas", personas);
        model.addAttribute("convocatorias", convocatorias);

        return "Conahcyt/Edit";
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveProyecto(@Valid ConahcytDto conahcytDto, BindingResult result, Model model) {

        var mod = result.getModel();
        if(result.hasErrors()){
            if (!mod.isEmpty()) {
                mod.forEach((String k, Object obj) -> {
                    System.out.println("Error key: " + k + " Obj:" + obj);
                });
            }
            List<Estado> estados = List.of(Estado.values());
            List<Empleado> personas = personalRepository.findAll();
            List<Convocatoria> convocatorias = convocatoriaRepository.findAll();

            model.addAttribute("proyecto", conahcytDto);
            model.addAttribute("estados", estados);
            model.addAttribute("personas", personas);
            model.addAttribute("convocatorias", convocatorias);
            return "Conahcyt/Edit";
        } else {
            ConahcytDto res = conahcytService.saveProyecto(conahcytDto);

            return "redirect:/conahcyt/view?id=" + res.getId();
        }
    }
}
