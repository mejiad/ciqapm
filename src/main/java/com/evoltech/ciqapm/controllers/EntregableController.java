

package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.repository.EntregableRepository;
import com.evoltech.ciqapm.repository.EtapaRepository;
import com.evoltech.ciqapm.utils.BreadcrumbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.PrivateKey;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("entregable")
public class EntregableController {

    @Autowired
    EntregableRepository entregableRepository;

    @Autowired
    EtapaRepository etapaRepository;

    public EntregableController(EntregableRepository entregableRepository) {
        this.entregableRepository = entregableRepository;
    }


    @GetMapping("list")
    private String lista(@RequestParam("id") Long id, Model model) {
        System.out.println("Id de list:" + id);
        Etapa etapa = etapaRepository.getReferenceById(id);
        List<Entregable> entregables = entregableRepository.findByEtapa(etapa);
        model.addAttribute("entregables", entregables);
        model.addAttribute("etapa", etapa);
        return "Entregable/List";
    }

    @GetMapping("new")
    private String nuevo(@RequestParam("id")Long id, Model model){
        Etapa etapa = etapaRepository.getReferenceById(id);
        Entregable entregable = new Entregable();
        entregable.setEtapa(etapa);

        BreadcrumbService breadcrumbService = new BreadcrumbService();
        String pathTipoProyecto = breadcrumbService.getPathTipoProyecto(etapa.getProyecto());
        String pathProyecto = breadcrumbService.getPathProyecto(etapa.getProyecto());
        String tagTipoProyecto = breadcrumbService.getTagTipoProyecto(etapa.getProyecto());
        String proyectoNombre = etapa.getProyecto().getNombre();

        model.addAttribute("entregable", entregable);
        model.addAttribute("etapa", etapa);
        model.addAttribute("pathTipoProyecto", pathTipoProyecto);
        model.addAttribute("pathProyecto", pathProyecto);
        model.addAttribute("tagTipoProyecto", tagTipoProyecto);
        model.addAttribute("proyectoNombre", proyectoNombre);

        return "Entregable/Edit";
    }


    @PostMapping("save")
    private String save(Entregable entregable, BindingResult result, Model model){
        if(result.hasErrors()){
            var mod = result.getModel();

            model.addAttribute("entregable", entregable);
            return "Entregable/Edit";
        }
        LocalDate hoy = LocalDate.now();
        entregable.setFechaCarga(hoy);
        if (entregable.getId() == null || entregable.getId() == 0) {
            entregableRepository.save(entregable);
        } else {
            entregableRepository.save(entregable);
        }
        //return "redirect:/proyecto/list";
        return "redirect:/etapa/view?id=" + entregable.getEtapa().getId();
    }



}


