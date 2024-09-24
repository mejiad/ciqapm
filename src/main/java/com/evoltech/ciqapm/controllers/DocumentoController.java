package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.Documento;
import com.evoltech.ciqapm.model.Personal;
import com.evoltech.ciqapm.repository.DocumentoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/documento")
public class DocumentoController {

    @Autowired
    DocumentoRepository documentoRepository;

    public DocumentoController(DocumentoRepository documentoRepository) {
        this.documentoRepository = documentoRepository;
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

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveDocumento(Documento documento, Model model){

        System.out.println("ID: " + documento.getId());
        documentoRepository.save(documento);

        return "redirect:/documento/list";
    }
}
