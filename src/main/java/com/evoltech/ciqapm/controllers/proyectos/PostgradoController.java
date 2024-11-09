package com.evoltech.ciqapm.controllers.proyectos;

import com.evoltech.ciqapm.dto.GanttDTO;
import com.evoltech.ciqapm.dto.PostgradoDto;
import com.evoltech.ciqapm.model.*;
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
    private final EmpleadoRepository personalRepository;
    @Autowired
    private final AlumnoRepository alumnoRepository;

    @Autowired
    private final PostgradoRepository postgradoRepository;

    @Autowired
    private final ConahcytRepository conahcytRepository;

    public PostgradoController(ProyectoServicio proyectoServicio, ProyectoRepository proyectoRepository,
                               EtapaRepository etapaRepository,
                               EmpleadoRepository personalRepository,
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
        DatosPostgrado datosPostgrado = postgradoRepository.findByProyecto(proyecto);

        List<Etapa> etapas = etapaRepository.findByProyecto(proyecto);

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

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("datosPostgrado", datosPostgrado);
        model.addAttribute("etapas", ganttDTOS);

        return "/Postgrado/View";
    }

    @GetMapping("/edit")
    public String editProyecto(Model model) {
        return "/Postgrado/Edit";
    }

    @GetMapping("/new")
    public String newPostgrado(Model model) {
        Proyecto proyecto = new Proyecto();

        List<Estado> estados = List.of(Estado.values());
        List<Empleado> personas = personalRepository.findAll();
        List<Alumno> alumnos = alumnoRepository.findAll();
        List<TipoProyecto> tiposProyecto = List.of(TipoProyecto.values());

        DatosPostgrado datosPostgrado = new DatosPostgrado();
        proyecto.setTipoProyecto(TipoProyecto.INDUSTRIA);
        PostgradoDto postgradoDto = new PostgradoDto();

        model.addAttribute("postgradoDto", postgradoDto);
        model.addAttribute("estados", estados);
        model.addAttribute("personas", personas);
        model.addAttribute("alumnos", alumnos);
        model.addAttribute("tiposProyecto", tiposProyecto);
        model.addAttribute("datosPostgrado", datosPostgrado);

        return "/Postgrado/Edit";
    }

    /*
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveDocumento(Documento documento, Model model){
    */

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveProyecto(@Valid PostgradoDto postgradoDto, BindingResult result, Model model) {
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
            List<Alumno> alumnos= alumnoRepository.findAll();
            //List<TipoProyecto> tiposProyecto = List.of(TipoProyecto.values());

            model.addAttribute("postgradoDto", postgradoDto);
            model.addAttribute("estados", estados);
            model.addAttribute("personas", personas);
            model.addAttribute("alumnos", alumnos);
            //model.addAttribute("tiposProyecto", tiposProyecto);
            return "/Postgrado/Edit";
        } else {
            // TODO: Crear el directorio del id del proyecto
            System.out.println("Inicio del save");
            Proyecto proyecto = postgradoDto.getProyecto();

            proyecto.setEstatus(Estado.PROCESO);

            DatosPostgrado postgrado = postgradoDto.getDatosPostgrado();

            Proyecto res = proyectoRepository.save(proyecto);
            postgrado.setProyecto(res);

            postgradoRepository.save(postgrado);

            res = proyectoRepository.save(res);

            new File("src/main/resources/directory/postgrado" + res.getId()).mkdirs();

            return "redirect:/postgrado/view?id=" + res.getId();
        }
       /* if(result.hasErrors()){
            System.out.println("Hay errores");
            return "/Postgrado/Edit";

        } else {
            System.out.println("NO Hay errores");
            Proyecto proyecto = postgradoDto.getProyecto();
            proyecto.setTipoProyecto(TipoProyecto.POSTGRADO);

            DatosPostgrado datosPostgrado = postgradoDto.getDatosPostgrado();

            var res = proyectoRepository.save(proyecto);
            datosPostgrado.setProyecto(res);

            postgradoRepository.save(datosPostgrado);

            new File("src/main/resources/directory/" + res.getId()).mkdirs();

            return "redirect:/proyecto/list";

        }*/

    }



}
