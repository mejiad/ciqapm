package com.evoltech.ciqapm.controllers.proyectos;

import com.evoltech.ciqapm.dto.GanttDTO;
import com.evoltech.ciqapm.dto.PostgradoDto;
import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.model.datos.DatosConahcyt;
import com.evoltech.ciqapm.model.datos.DatosPostgrado;
import com.evoltech.ciqapm.repository.*;
import com.evoltech.ciqapm.repository.datos.ConahcytRepository;
import com.evoltech.ciqapm.repository.datos.PostgradoRepository;
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
@RequestMapping("/postgrado")
public class PostgradoController {

    private static final Logger log = LogManager.getLogger(PostgradoController.class);

    @Autowired
    ProyectoServicio proyectoServicio;

    @Autowired
    ProyectoRepository proyectoRepository;

    @Autowired
    EtapaRepository etapaRepository;
    @Autowired
    private final PersonalRepository personalRepository;
    @Autowired
    private final AlumnoRepository alumnoRepository;

    @Autowired
    private final PostgradoRepository postgradoRepository;

    @Autowired
    private final ConahcytRepository conahcytRepository;

    public PostgradoController(ProyectoServicio proyectoServicio, ProyectoRepository proyectoRepository,
                               EtapaRepository etapaRepository,
                               PersonalRepository personalRepository,
                               ConahcytRepository conahcytRepository,
                               AlumnoRepository alumnoRepository, PostgradoRepository postgradoRepository) {
        this.proyectoServicio = proyectoServicio;
        this.proyectoRepository = proyectoRepository;
        this.etapaRepository = etapaRepository;
        this.personalRepository = personalRepository;
        this.alumnoRepository = alumnoRepository;
        this.conahcytRepository = conahcytRepository;
        this.postgradoRepository = postgradoRepository;
    }

    @GetMapping("/list")
    public String listProyecto(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println("Usuario loggeado:" + username);

        List<Proyecto> proyectos = proyectoRepository.findByTipoProyecto(TipoProyecto.POSTGRADO);

        model.addAttribute("proyectos", proyectos);

        return "/Postgrado/List";
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

        return "/Postgrado/View";
    }

    @GetMapping("/edit")
    public String editProyecto(Model model) {
        return "/Postgrado/Edit";
    }

    @GetMapping("/new")
    public String newPostgrado(Model model) {
        PostgradoDto proyecto = new PostgradoDto();
        List<Estado> estados = List.of(Estado.values());
        List<Personal> personas = personalRepository.findAll();
        List<Alumno> alumnos = alumnoRepository.findAll();
        List<TipoProyecto> tiposProyecto = List.of(TipoProyecto.values());

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("estados", estados);
        model.addAttribute("personas", personas);
        model.addAttribute("alumnos", alumnos);
        model.addAttribute("tiposProyecto", tiposProyecto);

        return "/Postgrado/Edit";
    }

    /*
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveDocumento(Documento documento, Model model){
    */

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveProyecto(@Valid PostgradoDto postgradoDto, BindingResult result, Model model) {

        if(result.hasErrors()){
            System.out.println("Hay errores");
            return "/Postgrado/Edit";

        } else {
            System.out.println("NO Hay errores");
            Proyecto proyecto = postgradoDto.getProyecto();
            proyecto.setTipoProyecto(TipoProyecto.POSTGRADO);

            DatosPostgrado datosPostgrado = postgradoDto.getDatosPostgrado();

            var res = proyectoRepository.save(proyecto);
            datosPostgrado.setProyecto(res);

            new File("src/main/resources/directory/" + res.getId()).mkdirs();

            return "redirect:/proyecto/list";

        }

    }



}
