package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.TipoConvocatoria;
import com.evoltech.ciqapm.model.TipoProyecto;
import com.evoltech.ciqapm.model.jpa.Convocatoria;
import com.evoltech.ciqapm.repository.ConvocatoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.util.JpaMetamodel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/convocatoria")
public class ConvocatoriaController {

    @Autowired
    ConvocatoriaRepository convocatoriaRepository;

    public ConvocatoriaController(ConvocatoriaRepository convocatoriaRepository) {
        this.convocatoriaRepository = convocatoriaRepository;
    }

    @GetMapping("/list")
    public String list(Model model){
        List<Convocatoria> convocatorias = convocatoriaRepository.findAll();

        model.addAttribute("convocatorias", convocatorias);
        return "Convocatoria/List";
    }

    @GetMapping("/view")
    public String view(@RequestParam("id") Long id, Model model){
        Convocatoria convocatoria = convocatoriaRepository.getReferenceById(id);
        model.addAttribute("convocatoria", convocatoria);
        return "Convocatoria/View";
    }

    @GetMapping("new")
    public String newConvocatoria(Model model){
        Convocatoria convocatoria = new Convocatoria();
        List<TipoConvocatoria> tipos = List.of(TipoConvocatoria.values());
        model.addAttribute("convocatoria", convocatoria);
        model.addAttribute("tipos", tipos);

        return "Convocatoria/Edit";
    }

    @PostMapping("save")
    public String save(Convocatoria convocatoria, Model model){
        convocatoriaRepository.save(convocatoria);
        return "redirect:/convocatoria/list";
    }
}
