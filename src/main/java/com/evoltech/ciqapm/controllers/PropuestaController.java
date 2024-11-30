package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.dto.PropuestaDto;
import com.evoltech.ciqapm.dto.PropuestaDtoInterface;
import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.repository.EmpleadoRepository;
import com.evoltech.ciqapm.repository.PropuestaRepository;
import com.evoltech.ciqapm.repository.ProyectoRepository;
import com.evoltech.ciqapm.repository.datos.ConahcytRepository;
import com.evoltech.ciqapm.service.ProyectoServicio;
import com.evoltech.ciqapm.service.StorageService;
import jakarta.transaction.Transactional
;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("propuesta")
public class PropuestaController {
    // Logger logger = LoggerFactory.getLogger(PropuestaController.class);
    Logger logger = LoggerFactory.getLogger(PropuestaController.class.getName());

    @Autowired
    PropuestaRepository propuestaRepository;

    @Autowired
    ConahcytRepository proyectoRepository;

    @Autowired
    EmpleadoRepository personalRepository;

    @Autowired
    StorageService storageService;

    @Autowired
    ProyectoServicio proyectoServicio;



    public PropuestaController(PropuestaRepository propuestaRepository, ConahcytRepository proyectoRepository, EmpleadoRepository personalRepository, StorageService storageService,
                               ProyectoServicio proyectoServicio) {
        this.propuestaRepository = propuestaRepository;
        this.proyectoRepository = proyectoRepository;
        this.personalRepository = personalRepository;
        this.storageService = storageService;
        this.proyectoServicio = proyectoServicio;
    }

    @GetMapping("list/{id}")
    private String listapath(@PathVariable("id") Long id, Model model){
        logger.info("Dentro de PropuestaController Listapath");
        Proyecto proyecto = proyectoRepository.getReferenceById(id);
        Collection<PropuestaDtoInterface> propuestaDtos = propuestaRepository.findByProyecto(proyecto, PropuestaDtoInterface.class);
        List<Empleado> personas = personalRepository.findAll();
        List<Estado> estados = List.of(Estado.values());

        model.addAttribute("personas", personas);
        model.addAttribute("propuestas", propuestaDtos);
        model.addAttribute("proyecto", proyecto);

        return "Propuesta/List";
    }

    @GetMapping("lista")
    private String lista(@RequestParam("id") Long id, Model model){
        logger.info("Dentro de PropuestaController List:" + id);
        Proyecto proyecto = proyectoRepository.getReferenceById(id);
        Collection<PropuestaDtoInterface> propuestaDtos = propuestaRepository.findByProyecto(proyecto, PropuestaDtoInterface.class);
        List<Empleado> personas = personalRepository.findAll();
        List<Estado> estados = List.of(Estado.values());

        model.addAttribute("personas", personas);
        model.addAttribute("propuestas", propuestaDtos);
        model.addAttribute("proyecto", proyecto);

        return "Propuesta/List";
    }

    @GetMapping("new")
    private String nuevo(@RequestParam("id") Long id, Model model) {
        Proyecto proyecto = proyectoRepository.getReferenceById(id);

        Propuesta propuesta = new Propuesta();
        List<Empleado> personas = personalRepository.findAll();
        List<Estado> estados = List.of(Estado.values());

        model.addAttribute("estados", estados);
        model.addAttribute("personas", personas);
        model.addAttribute("propuesta", propuesta);
        model.addAttribute("proyecto", proyecto);

        return "Propuesta/Edit";
    }

    @GetMapping("edit")
    private String edit(@RequestParam("id") Long id, Model model) {
        Propuesta propuesta = propuestaRepository.getReferenceById(id);

        model.addAttribute("propuesta", propuesta);

        return "Propuesta/View";
    }

    @GetMapping("view")
    private String view(@RequestParam("id") Long id, Model model) {
        Propuesta propuesta = propuestaRepository.getReferenceById(id);

        model.addAttribute("propuesta", propuesta);

        return "Propuesta/View";
    }


    @PostMapping("save")
    private String salvar(@Valid Propuesta propuesta, BindingResult result, Model model) {

        if (result.hasErrors()) {
            var mod = result.getModel();
            Proyecto proyecto = propuesta.getProyecto();

            List<Empleado> personas = personalRepository.findAll();
            List<Estado> estados = List.of(Estado.values());

            model.addAttribute("estados", estados);
            model.addAttribute("personas", personas);
            model.addAttribute("propuesta", propuesta);
            model.addAttribute("proyecto", proyecto);

            return "Propuesta/Edit";
        }
        Propuesta res = propuestaRepository.save(propuesta);
        return "redirect:/conahcyt/view/" + res.getProyecto().getId();
    }

    @GetMapping("uploadform")
    public String load(@RequestParam("propuestaId") Long propuestaId, Model model){

        Propuesta propuesta = propuestaRepository.getReferenceById(propuestaId);

        model.addAttribute("propuesta", propuesta);

        return "Propuesta/Loadform";
    }

    // @Transactional
    @PostMapping(value =  "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestBody MultipartFile file,
                @RequestParam String id)
    {
        Long propuestaId = Long.parseLong(id);

        Propuesta propuesta = propuestaRepository.getReferenceById(propuestaId);

        byte[] contenido = storageService.storeDB(file);
        propuesta.setData(contenido);
        propuesta.setFechaCargaDocumento(LocalDate.now());
        var res = proyectoServicio.save(propuesta);

        return "redirect:/conahcyt/view/" + propuesta.getProyecto().getId();
    }

}
