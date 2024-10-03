package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.Documento;
import com.evoltech.ciqapm.model.Personal;
import com.evoltech.ciqapm.model.Proyecto;
import com.evoltech.ciqapm.repository.DocumentoRepository;
import com.evoltech.ciqapm.repository.ProyectoRepository;
import com.evoltech.ciqapm.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.security.cert.TrustAnchor;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/documento")
public class DocumentoController {

    @Autowired
    DocumentoRepository documentoRepository;

    @Autowired
    StorageService storageService;

    @Autowired
    ProyectoRepository proyectoRepository;

    public DocumentoController(DocumentoRepository documentoRepository, StorageService storageService) {
        this.documentoRepository = documentoRepository;
        this.storageService = storageService;
    }

    @GetMapping("/list")
    public String listDocumento(@RequestParam("id") Long id, Model model){
        Proyecto proyecto = proyectoRepository.getReferenceById(id);
        List<Documento> documento = documentoRepository.findByProyecto(proyecto);

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("documento", documento);

        return "/Documento/List";
    }

    @GetMapping("/view")
    public String viewDocumento(@RequestParam("id") Long id, Model model){
        Documento documento = new Documento();
        try {
            documento = documentoRepository.getReferenceById(id);
        } catch(Exception e){
            log.error("Error al leer Personal con id:" + id);
        }
        model.addAttribute("documento", documento);
        return "Documento/View";
    }

    @GetMapping("/edit")
    public String editDocumento(Model model){
        return "/Documento/Edit";
    }

    @GetMapping("/new")
    public String newDocumento(@RequestParam("id") Long id, Model model){
        Documento documento = new Documento();
        Proyecto proyecto = proyectoRepository.getReferenceById(id);
        documento.setProyecto(proyecto);

        model.addAttribute("documento", documento);
        model.addAttribute("proyecto", proyecto);

        return "/Documento/Edit";
    }

    /*
     necesitamos el archivo en multipart además de los datos que se
     deben guardar en la base de datos.
     Se debe procesar para guardar en el directorio del proyetco
     1. Generar el directorio del proyecto al crear un nuevo proyecto.
     */
    /*
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String savePersonal(Personal persona, Model model){
     */
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveDocumento(@RequestBody MultipartFile file,
                                @RequestParam String nombre,
                                @RequestParam String descripcion,
                                @RequestParam String proyecto
                                ) {

        Documento documento = new Documento();

        System.out.println("Documento salvado ID: " + nombre);
        System.out.println("File: " + file.getOriginalFilename());
        System.out.println("Name: " + nombre.toString());
        System.out.println("Descripcion: " + descripcion.toString());
        System.out.println("Proyecto: " + proyecto.toString());
        Long proyectoId = Long.parseLong(proyecto.toString());
        Proyecto proyectoObj = proyectoRepository.getReferenceById(proyectoId);
        documento.setNombreArchivo(file.getOriginalFilename());
        documento.setDescripcion(descripcion.toString());
        documento.setNombre(nombre.toString());
        documento.setProyecto(proyectoObj);
        documentoRepository.save(documento);

        storageService.store(file);

        return "redirect:/proyecto/list";
    }
}
