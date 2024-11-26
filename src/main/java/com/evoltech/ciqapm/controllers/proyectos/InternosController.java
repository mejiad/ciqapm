package com.evoltech.ciqapm.controllers.proyectos;

import com.evoltech.ciqapm.dto.GanttDTO;
import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.model.datos.DatosInternos;
import com.evoltech.ciqapm.repository.*;
import com.evoltech.ciqapm.repository.datos.ConahcytRepository;
import com.evoltech.ciqapm.repository.datos.InternoRepository;
import com.evoltech.ciqapm.service.ProyectoServicio;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/interno")
public class InternosController {

    private static final Logger log = LogManager.getLogger(InternosController.class);

    @Autowired
    ProyectoServicio proyectoServicio;

    @Autowired
    EtapaRepository etapaRepository;

    @Autowired
    private final EmpleadoRepository personalRepository;

    @Autowired
    private final ClienteRepository clienteRepository;

    @Autowired
    private final InternoRepository internoRepository;

    public InternosController(ProyectoServicio proyectoServicio, ProyectoRepository proyectoRepository,
                              EtapaRepository etapaRepository,
                              EmpleadoRepository personalRepository,
                              InternoRepository internoRepository,
                              ClienteRepository clienteRepository) {
        this.proyectoServicio = proyectoServicio;
        this.etapaRepository = etapaRepository;
        this.personalRepository = personalRepository;
        this.clienteRepository = clienteRepository;
        this.internoRepository = internoRepository;
    }

    @GetMapping("/list")
    public String listProyecto(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        List<Interno> proyectos = internoRepository.findAll();
        model.addAttribute("proyectos", proyectos);
        return "Interno/List";
    }

    @GetMapping("/view")
    public String viewProyecto(@RequestParam("id") Long id, Model model) {
        String pattern = "YYYY MM dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println("Nombre del usuario: " + username);

        Interno proyecto = internoRepository.getReferenceById(id);
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
        model.addAttribute("etapas", ganttDTOS);

        return "Interno/View";
    }

    @GetMapping("/edit")
    public String editProyecto(Model model) {
        return "Interno/Edit";
    }




    @GetMapping("/new")
    public String newInternos(Model model) {
        Interno proyecto = new Interno();
        proyecto.setNombre("Primer Proyecto de prueba.");
        proyecto.setDescripcion("Descripción del proyecto.");
        List<Estado> estados = List.of(Estado.values());
        List<Empleado> personas = personalRepository.findAll();
        List<Cliente> clientes = clienteRepository.findAll();
        List<TipoProyecto> tiposProyecto = List.of(TipoProyecto.values());
        DatosInternos internos = new DatosInternos();
        proyecto.setTipoProyecto(TipoProyecto.INTERNO);

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("estados", estados);
        model.addAttribute("personas", personas);
        model.addAttribute("clientes", clientes);
        model.addAttribute("tiposProyecto", tiposProyecto);
        model.addAttribute("internos", internos);

        return "Interno/Edit";
    }

    /*
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveDocumento(Documento documento, Model model){
    */

    // TODO: Borrar
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveProyectoOld(Interno proyecto, Model model) {

        Interno res = internoRepository.save(proyecto);

        System.out.println("ID del nuevo proyecto: " + res.getId());
        new File("src/main/resources/directory/" + res.getId()).mkdirs();
        return "redirect:/interno/list";
    }

    @PostMapping(value = "/saveInterno", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveProyecto(Interno proyecto, Model model) {

        // TODO: Crear el directorio del id del proyecto

        proyecto.setTipoProyecto(TipoProyecto.INTERNO);
        Interno res = internoRepository.save(proyecto);

        System.out.println("ID del nuevo proyecto: " + res.getId());
        new File("src/main/resources/directory/" + res.getId()).mkdirs();
        return "redirect:/proyecto/list";
    }
}
