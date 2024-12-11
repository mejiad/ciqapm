package com.evoltech.ciqapm.controllers;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("propuesta")
public class PropuestaController {

    @Autowired
    PropuestaRepository propuestaRepository;

    // @Autowired
    // private final ConahcytRepository conahcytRepository;

    @Autowired
    EmpleadoRepository personalRepository;

    @Autowired
    ProyectoRepository proyectoRepository;

    @Autowired
    ProyectoServicio proyectoServicio;

    @Autowired
    StorageService storageService;

    public PropuestaController(PropuestaRepository propuestaRepository,
                               // ConahcytRepository conahcytRepository,
                               ProyectoRepository proyectoRepository,
                               EmpleadoRepository personalRepository,
                               StorageService storageService,
                               ProyectoServicio proyectoServicio) {
        this.propuestaRepository = propuestaRepository;
        // this.conahcytRepository = conahcytRepository;
        this.proyectoRepository = proyectoRepository;
        this.personalRepository = personalRepository;
        this.storageService = storageService;
        this.proyectoServicio = proyectoServicio;
    }

    @GetMapping("list/{id}")
    private String listapath(@PathVariable("id") Long id, Model model){
        // logger.info("Dentro de PropuestaController Listapath");
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
    public String lista(@RequestParam("id") Long id, Model model){
        System.out.println("Dentro de PropuestaController Lista:" + id);

        List<Empleado> personas = personalRepository.findAll();
        Proyecto proyecto = proyectoRepository.getReferenceById(id);
        Collection<PropuestaDtoInterface> propuestaDtos = propuestaRepository.findByProyecto(proyecto, PropuestaDtoInterface.class);

        model.addAttribute("personas", personas);
        model.addAttribute("propuestas", propuestaDtos);
        model.addAttribute("proyecto", proyecto);

        return "Propuesta/List";
    }

    @GetMapping("listaOld")
    private String listaOld(@RequestParam("id") Long id, Model model){
        System.out.println("Dentro de PropuestaController Lista:" + id);

        Proyecto proyecto = proyectoRepository.getReferenceById(id);
        Collection<PropuestaDtoInterface> propuestaDtos = propuestaRepository.findByProyecto(proyecto, PropuestaDtoInterface.class);
        List<Empleado> personas = personalRepository.findAll();

        model.addAttribute("personas", personas);
        model.addAttribute("propuestas", propuestaDtos);
        model.addAttribute("proyecto", proyecto);

        return "Propuesta/List";
    }

    @GetMapping("new")
    public String nuevo(@RequestParam("id") Long id, Model model) {
        System.out.println("New propuesta:" + id);
        List<Estado> estados = List.of(Estado.values());

        List<Empleado> personas = personalRepository.findAll();
        Proyecto proyecto = proyectoRepository.getReferenceById(id);
        Propuesta propuesta = new Propuesta();

        model.addAttribute("estados", estados);
        model.addAttribute("personas", personas);
        model.addAttribute("propuesta", propuesta);
        model.addAttribute("proyecto", proyecto);

        return "Propuesta/Edit";
    }

    @GetMapping("edit")
    public String edit(@RequestParam("id") Long id, Model model) {
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
    public String salvar(@Valid Propuesta propuesta, BindingResult result, Model model) {

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
        String retVal = switch (res.getProyecto().getTipoProyecto()){
            case CONAHCYT -> "redirect:/conahcyt/" + res.getProyecto().getId() + "/view?tab=propuestaTab";
            case INDUSTRIA -> "redirect:/industria/" + res.getProyecto().getId() + "/view?tab=propuestaTab";
            case INTERNO -> "redirect:/interno/" + res.getProyecto().getId() + "/view?tab=propuestaTab";
            case POSTGRADO -> "redirect:/interno/" + res.getProyecto().getId() + "/view?tab=propuestaTab";
        };

        return retVal;
    }


    @GetMapping("editRespuesta")
    public String editRespuesta(@RequestParam("propuestaId") Long propuestaId, Model model) {
        Propuesta propuesta = propuestaRepository.getReferenceById(propuestaId);
        Proyecto proyecto = propuesta.getProyecto();
        List<Estado> estados = List.of(Estado.values());
        List<Empleado> personas = personalRepository.findAll();
        List<ConahcytRespuesta> respuestas = List.of(ConahcytRespuesta.values());

        model.addAttribute("propuesta", propuesta);
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("estados", estados);
        model.addAttribute("personas", personas);
        model.addAttribute("respuestas", respuestas);

        return "Propuesta/Respuesta";
    }

    @PostMapping("saveRespuesta")
    public String salvarRespuesta(@Valid Propuesta propuesta, BindingResult result, Model model) {

        if (result.hasErrors()) {
            var mod = result.getModel();
            Proyecto proyecto = propuesta.getProyecto();

            List<Empleado> personas = personalRepository.findAll();
            List<Estado> estados = List.of(Estado.values());
            List<ConahcytRespuesta> respuestas = List.of(ConahcytRespuesta.values());

            model.addAttribute("estados", estados);
            model.addAttribute("personas", personas);
            model.addAttribute("propuesta", propuesta);
            model.addAttribute("proyecto", proyecto);
            model.addAttribute("respuestas", respuestas);

            return "Propuesta/Edit";
        }
        Propuesta res = propuestaRepository.save(propuesta);
        String retVal = switch (res.getProyecto().getTipoProyecto()){
            case CONAHCYT -> "redirect:/conahcyt/" + res.getProyecto().getId() + "/view?tab=propuestaTab";
            case INDUSTRIA -> "redirect:/industria/" + res.getProyecto().getId() + "/view?tab=propuestaTab";
            case INTERNO -> "redirect:/interno/" + res.getProyecto().getId() + "/view?tab=propuestaTab";
            case POSTGRADO -> "redirect:/interno/" + res.getProyecto().getId() + "/view?tab=propuestaTab";
        };

        return retVal;
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
        propuesta.setNombreArchivo(file.getOriginalFilename());
        propuesta.setData(contenido);
        propuesta.setFechaCargaDocumento(LocalDate.now());

        var res = proyectoServicio.save(propuesta);

        return "redirect:/conahcyt/view/" + propuesta.getProyecto().getId();
    }

    @Transactional
    @GetMapping( value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE )
    public ResponseEntity download(@RequestParam("propuestaId") Long id) throws IOException {

        // String fileName = URLEncoder.encode(tchCeResource.getRname(), "UTF-8");
        // fileName = URLDecoder.decode(fileName, "ISO8859_1");
        // response.setContentType("application/x-msdownload");
        // response.setHeader("Content-disposition", "attachment; filename="+ filename);

        Propuesta documento = propuestaRepository.getReferenceById(id);

        String filename = documento.getNombreArchivo();
        System.out.println("Filename:" + filename);

        Path path = storageService.load(filename);
        // Resource resource = storageService.loadAsResource(filename);

        // byte[] contenido = resource.getContentAsByteArray();
        byte[] contenido = documento.getData();

        System.out.println("Path: " + path.toAbsolutePath());

        return ResponseEntity.ok().header("Content-disposition", "attachment; filename="+ filename).body(contenido);
    }

}
