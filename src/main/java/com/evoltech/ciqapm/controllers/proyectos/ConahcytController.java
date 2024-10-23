package com.evoltech.ciqapm.controllers.proyectos;

import com.evoltech.ciqapm.dto.ConahcytDto;
import com.evoltech.ciqapm.dto.GanttDTO;
import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.model.datos.DatosConahcyt;
import com.evoltech.ciqapm.model.jpa.Convocatoria;
import com.evoltech.ciqapm.repository.*;
import com.evoltech.ciqapm.repository.datos.ConahcytRepository;
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
@RequestMapping("/conahcyt")
public class ConahcytController {

    private static final Logger log = LogManager.getLogger(ConahcytController.class);

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

    @Autowired
    private final ConvocatoriaRepository convocatoriaRepository;

    public ConahcytController(ProyectoServicio proyectoServicio, ProyectoRepository proyectoRepository,
                              EtapaRepository etapaRepository,
                              PersonalRepository personalRepository,
                              ConahcytRepository conahcytRepository,
                              ConvocatoriaRepository convocatoriaRepository,
                              ClienteRepository clienteRepository) {
        this.proyectoServicio = proyectoServicio;
        this.proyectoRepository = proyectoRepository;
        this.etapaRepository = etapaRepository;
        this.personalRepository = personalRepository;
        this.clienteRepository = clienteRepository;
        this.convocatoriaRepository = convocatoriaRepository;
        this.conahcytRepository = conahcytRepository;
    }

    @GetMapping("/list")
    public String listProyecto(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println("Conahcyt Usuario loggeado:" + username);

        List<Proyecto> proyectos = proyectoRepository.findByTipoProyecto(TipoProyecto.CONAHCYT);

        model.addAttribute("proyectos", proyectos);

        return "/Conahcyt/List";
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
        DatosConahcyt datosConahcyt = conahcytRepository.findByProyecto(proyecto);
        ConahcytDto conahcytDto = new ConahcytDto(proyecto, datosConahcyt);
        System.out.println("Descripcion: " + conahcytDto.getDescripcion());
        System.out.println("Descripcion Proyecto: " + proyecto.getDescripcion());
        int avance = proyecto.getAvance();

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

        model.addAttribute("conahcytDto", conahcytDto);
        model.addAttribute("etapas", ganttDTOS);
        model.addAttribute("avance", avance);

        return "/Conahcyt/View";
    }

    @GetMapping("/edit")
    public String editProyecto(Model model) {
        return "/Proyecto/Edit";
    }


    @GetMapping("/new")
    public String newConahcyt(Model model) {
        ConahcytDto proyecto = new ConahcytDto();
        List<Estado> estados = List.of(Estado.values());
        List<Personal> personas = personalRepository.findAll();
        List<Cliente> clientes = clienteRepository.findAll();
        List<TipoProyecto> tiposProyecto = List.of(TipoProyecto.values());
        List<Convocatoria> convocatorias = convocatoriaRepository.findAll();
        DatosConahcyt conahcyt = new DatosConahcyt();

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("estados", estados);
        model.addAttribute("personas", personas);
        model.addAttribute("clientes", clientes);
        model.addAttribute("tiposProyecto", tiposProyecto);
        model.addAttribute("convocatorias", convocatorias);
        model.addAttribute("conahcyt", conahcyt);

        return "/Conahcyt/Edit";
    }

    /*
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveDocumento(Documento documento, Model model){
    */

    /*
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveProyecto(Proyecto proyecto, Model model) {

        // TODO: Crear el directorio del id del proyecto
        Proyecto res = proyectoRepository.save(proyecto);

        System.out.println("ID del nuevo proyecto: " + res.getId());
        new File("src/main/resources/directory/" + res.getId()).mkdirs();
        return "redirect:/proyecto/list";
    }
     */

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveProyecto(@Valid ConahcytDto conacytProyecto, BindingResult result, Model model) {


        var mod = result.getModel();
        if(result.hasErrors()){
            if (!mod.isEmpty()) {
                mod.forEach((String k, Object obj) -> {
                    System.out.println("Error key: " + k + " Obj:" + obj);
                    System.out.println("++++++++++++++++++++++++");
                });
            }
            List<Estado> estados = List.of(Estado.values());
            List<Personal> personas = personalRepository.findAll();
            List<Cliente> clientes = clienteRepository.findAll();
            List<TipoProyecto> tiposProyecto = List.of(TipoProyecto.values());
            List<Convocatoria> convocatorias = convocatoriaRepository.findAll();

            model.addAttribute("proyecto", conacytProyecto);
            model.addAttribute("estados", estados);
            model.addAttribute("personas", personas);
            model.addAttribute("clientes", clientes);
            model.addAttribute("convocatorias", convocatorias);
            model.addAttribute("tiposProyecto", tiposProyecto);
            return "/Conahcyt/Edit";
        } else {
            // TODO: Crear el directorio del id del proyecto
            System.out.println("Inicio del save");
            Proyecto proyecto = conacytProyecto.proyecto();

            proyecto.setEstatus(Estado.PROCESO);

            DatosConahcyt conahcyt = conacytProyecto.conahcyt();

            Proyecto res = proyectoRepository.save(proyecto);
            conahcyt.setProyecto(res);

            conahcytRepository.save(conahcyt);

            res = proyectoRepository.save(res);

            new File("src/main/resources/directory/conahcyt" + res.getId()).mkdirs();

            return "redirect:/conahcyt/view?id=" + res.getId();
        }
    }
}
