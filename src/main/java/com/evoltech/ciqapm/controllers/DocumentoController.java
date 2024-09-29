package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.Documento;
import com.evoltech.ciqapm.model.Personal;
import com.evoltech.ciqapm.repository.DocumentoRepository;
import com.evoltech.ciqapm.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/documento")
public class DocumentoController {

    @Autowired
    DocumentoRepository documentoRepository;

    @Autowired
    StorageService storageService;

    public DocumentoController(DocumentoRepository documentoRepository, StorageService storageService) {
        this.documentoRepository = documentoRepository;
        this.storageService = storageService;
    }

    @GetMapping("/list")
    public String listDocumento(Model model){

        List<Documento> documento = documentoRepository.findAll();
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
    public String newDocumento(Model model){
        Documento documento = new Documento();
        model.addAttribute("documento", documento);

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
                                @RequestParam String nombre) {

        // System.out.println("Documento salvado ID: " + documento.getNombre());")
        System.out.println("File: " + file.getOriginalFilename());
        System.out.println("Name: " + nombre.toString());
        storageService.store(file);

        return "redirect:/proyecto/list";
    }
}
