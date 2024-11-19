package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.repository.DocumentoRepository;
import com.evoltech.ciqapm.repository.ProyectoRepository;
import com.evoltech.ciqapm.service.DatabaseStorageService;
import com.evoltech.ciqapm.service.StorageService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.EntityResponse;

import javax.print.Doc;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.security.cert.TrustAnchor;
import java.util.ArrayList;
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

    public DocumentoController(DocumentoRepository documentoRepository, DatabaseStorageService storageService) {
        this.documentoRepository = documentoRepository;
        this.storageService = storageService;
    }

    @GetMapping("/list")
    public String listDocumento(@RequestParam("id") Long id, Model model){
        Proyecto proyecto = proyectoRepository.getReferenceById(id);

        List<Documento> documentos = documentoRepository.findByProyecto(proyecto);
        // List<Documento> documentos = new ArrayList<>();

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("documentos", documentos);

        return "/Documento/List";
    }

    @GetMapping("/view")
    public String viewDocumento(@RequestParam("id") Long id, Model model){
        Documento documento = new Documento();
        try {
            documento = documentoRepository.getReferenceById(id);
        } catch(Exception e){
            log.error("Error al leer Empleado con id:" + id);
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
        List<TipoDocumento> tiposDocumento = List.of(TipoDocumento.values());
        documento.setProyecto(proyecto);

        model.addAttribute("documento", documento);
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("tiposDocumento", tiposDocumento);

        return "/Documento/Edit";
    }

    @GetMapping("/propuesta")
    public String newPropuesta(@RequestParam("id") Long id, Model model){
        Documento documento = new Documento();
        Proyecto proyecto = proyectoRepository.getReferenceById(id);
        List<TipoDocumento> tiposDocumento = List.of(TipoDocumento.values());
        documento.setProyecto(proyecto);

        model.addAttribute("documento", documento);
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("tiposDocumento", tiposDocumento);

        return "/Documento/Edit";
    }

    /*
     necesitamos el archivo en multipart además de los datos que se
     deben guardar en la base de datos.
     Se debe procesar para guardar en el directorio del proyetco
     1. Generar el directorio del proyecto al crear un nuevo proyecto.
     */
    /*56
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveEmpleado(Empleado empleado, Model model){
     */
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveDocumento(@RequestBody MultipartFile file,
                                @RequestParam String nombre,
                                @RequestParam String descripcion,
                                @RequestParam String tipoDocumento,
                                @RequestParam String proyecto
                                ) {

        Documento documento = new Documento();

        System.out.println("Documento salvado nombre: " + nombre);
        System.out.println("File: " + file.getOriginalFilename());
        System.out.println("Name: " + nombre.toString());
        System.out.println("Descripcion: " + descripcion.toString());
        System.out.println("Proyecto: " + proyecto.toString());
        System.out.println("TipoDocumento: " + tipoDocumento.toString());

        Long proyectoId = Long.parseLong(proyecto.toString());
        Proyecto proyectoObj = proyectoRepository.getReferenceById(proyectoId);
        documento.setNombreArchivo(file.getOriginalFilename());
        documento.setDescripcion(descripcion.toString());
        documento.setNombre(nombre.toString());
        documento.setProyecto(proyectoObj);
        documento.setTipoDocumento(TipoDocumento.valueOf(tipoDocumento));

        byte[] contenido = storageService.storeDB(file);
        System.out.println("Longitud: " + contenido.length);
        documento.setNombreArchivo(file.getOriginalFilename());
        documento.setData(contenido);
        Documento doc =  documentoRepository.save(documento);

        return "redirect:/proyecto/list";
    }

    @Transactional
    @GetMapping( value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE )
    public ResponseEntity download(@RequestParam("id") Long id) throws IOException {

        // String fileName = URLEncoder.encode(tchCeResource.getRname(), "UTF-8");
        // fileName = URLDecoder.decode(fileName, "ISO8859_1");
        // response.setContentType("application/x-msdownload");
        // response.setHeader("Content-disposition", "attachment; filename="+ filename);

        Documento documento = documentoRepository.getReferenceById(id);

        String filename = documento.getNombreArchivo();
        System.out.println("Filename:" + filename);

        Path path = storageService.load(filename);
        // Resource resource = storageService.loadAsResource(filename);

        // byte[] contenido = resource.getContentAsByteArray();
        byte[] contenido = documento.getData();
        System.out.println("Contenido:" + contenido.toString());

        System.out.println("Path: " + path.toAbsolutePath());

        return ResponseEntity.ok().header("Content-disposition", "attachment; filename="+ filename).body(contenido);
    }
}
