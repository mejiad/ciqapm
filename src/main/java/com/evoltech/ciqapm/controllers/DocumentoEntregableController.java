package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.model.documentos.DocumentoEntregable;
import com.evoltech.ciqapm.repository.DocumentoEntregableRepository;
import com.evoltech.ciqapm.repository.EntregableRepository;
import com.evoltech.ciqapm.repository.EtapaRepository;
import com.evoltech.ciqapm.repository.ProyectoRepository;
import com.evoltech.ciqapm.service.StorageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("documentoEntregable")
@Transactional
public class DocumentoEntregableController {

    @Autowired
    EntregableRepository entregableRepository;

    @Autowired
    ProyectoRepository proyectoRepository;

    @Autowired
    EtapaRepository etapaRepository;

    @Autowired
    StorageService storageService;
    @Autowired
    private DocumentoEntregableRepository documentoEntregableRepository;

    public DocumentoEntregableController(EntregableRepository entregableRepository, ProyectoRepository proyectoRepository, EtapaRepository etapaRepository, StorageService storageService) {
        this.entregableRepository = entregableRepository;
        this.proyectoRepository = proyectoRepository;
        this.etapaRepository = etapaRepository;
        this.storageService = storageService;
    }

    @GetMapping("list")
    public String lista(@RequestParam("etapaId") Long etapaId, Model model) {
        Etapa etapa = etapaRepository.getReferenceById(etapaId);
        List<Entregable> entregables = entregableRepository.findByEtapa(etapa);

        model.addAttribute("entregables", entregables);

        return "DocumentoEntregable/List";
    }

    @GetMapping("uploadform")
    public String uploadform(@RequestParam("entregableId") Long entregableId, Model model) {
        Entregable entregable = entregableRepository.getReferenceById(entregableId);
        System.out.println("Forma para subir entregable:" + entregable.getNombre());
        model.addAttribute("entregable", entregable);

        return "DocumentoEntregable/Edit";
    }

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveDocumento(@RequestBody MultipartFile file,
                                @RequestParam String nombre,
                                @RequestParam String descripcion,
                                @RequestParam Long id   // id del entregable
    ) {

        Entregable entregable = entregableRepository.getReferenceById(id);

        System.out.println("Entregable nombre: " + entregable.getNombre());
        Etapa etapa = entregable.getEtapa();
        System.out.println("File: " + file.getOriginalFilename());
        System.out.println("Descripcion: " + descripcion.toString());

        entregable.setNombreArchivo(file.getOriginalFilename());
        entregable.setFechaCarga(LocalDate.now());

        byte[] contenido = storageService.storeDB(file);
        System.out.println("Longitud: " + contenido.length);

        entregable.setData(contenido);
        entregableRepository.save(entregable);

        return "redirect:/etapa/view/" + etapa.getId();
    }

    @Transactional
    @GetMapping( value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE )
    public ResponseEntity download(@RequestParam("entregableId") Long id) throws IOException {

        // String fileName = URLEncoder.encode(tchCeResource.getRname(), "UTF-8");
        // fileName = URLDecoder.decode(fileName, "ISO8859_1");
        // response.setContentType("application/x-msdownload");
        // response.setHeader("Content-disposition", "attachment; filename="+ filename);

        Entregable documento = entregableRepository.getReferenceById(id);

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
