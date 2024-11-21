package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.repository.EmpleadoRepository;
import com.evoltech.ciqapm.repository.datos.ConahcytRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("autoriza")
public class AutorizaController {

    @Autowired
    ConahcytRepository conahcytRepository;

    @Autowired
    EmpleadoRepository empleadoRepository;

    public AutorizaController(ConahcytRepository conahcytRepository) {
        this.conahcytRepository = conahcytRepository;
    }

    @GetMapping("list")
    public String lista(@RequestParam("id") Long id, Model model) {
        List<Autoriza> autorizaciones = new ArrayList<>();
        Conahcyt conahcyt = conahcytRepository.getReferenceById(id);
        System.out.println("Conahcyt nombre:" + conahcyt.getNombre());
        Autoriza depto = conahcyt.getDeptoAutoriza();
        Autoriza invest = conahcyt.getInvAutoriza();
        Autoriza dirgral = conahcyt.getDirAutoriza();
        if(depto != null){
            System.out.println("Deptpo no es null");
            autorizaciones.add(depto);
        }
        if(invest != null){
            System.out.println("Invest no es null");
            autorizaciones.add(invest);
        }
        if(dirgral != null){
            System.out.println("DirGral no es null");
            autorizaciones.add(dirgral);
        }


        model.addAttribute("proyecto", conahcyt);
        model.addAttribute("autorizaciones", autorizaciones);

        return "Autoriza/List";
    }

    @GetMapping("new")
    public String nuevo(@RequestParam("id") Long id, Model model){
        Autoriza autoriza = new Autoriza();
        Conahcyt conahcyt = conahcytRepository.getReferenceById(id);
        List<Empleado> personas = empleadoRepository.findAll();
        List<AutorizaNivel> niveles = List.of(AutorizaNivel.values());
        model.addAttribute("autoriza", autoriza);
        model.addAttribute("proyecto", conahcyt);
        model.addAttribute("niveles", niveles);
        model.addAttribute("personas", personas);

        return "Autoriza/Edit";
    }

    @GetMapping("edit")
    public String edit(Autoriza autoriza, Conahcyt conahcyt, Model model){

        List<AutorizaNivel> niveles = List.of(AutorizaNivel.values());
        model.addAttribute("autoriza", autoriza);
        model.addAttribute("proyecto", conahcyt);
        model.addAttribute("niveles", niveles);

        return "Autoriza/Edit";
    }

    @PostMapping("save")
    public String save(Autoriza autoriza, Proyecto proyecto, Model model) {
        Conahcyt conahcyt = conahcytRepository.getReferenceById(proyecto.getId());
        System.out.println("Proyecto id:" + proyecto.getId());
        System.out.println("Proyecto nombre:" + conahcyt.getNombre());

        switch(autoriza.getNivel()) {
            case AutorizaNivel.DEPARTAMENTO:
                System.out.println("DEPARTAMENTO");
                conahcyt.setDeptoAutoriza(autoriza);
                break;
            case AutorizaNivel.DIRECCION_INVESTIGACION :
                System.out.println("INVESTIGACION");
                conahcyt.setInvAutoriza(autoriza);
                break;
            case AutorizaNivel.DIRECCION_GENERAL:
                System.out.println("DIRECCION");
                conahcyt.setDirAutoriza(autoriza);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + autoriza.getNivel());
        }

        Conahcyt res = conahcytRepository.save(conahcyt);
        return "redirect:/conahcyt/list";
    }

}
