package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.Alumno;
import com.evoltech.ciqapm.model.Etapa;
import com.evoltech.ciqapm.model.Proyecto;
import com.evoltech.ciqapm.model.Servicio;
import com.evoltech.ciqapm.repository.ServicioRepository;
import com.evoltech.ciqapm.service.ServicioService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/servicio")
public class ServicioController {
    @Autowired
    ServicioService servicioService;
    private final ServicioRepository servicioRepository;

    public ServicioController(ServicioService servicioService,
                              ServicioRepository servicioRepository) {
        this.servicioService = servicioService;
        this.servicioRepository = servicioRepository;
    }

    @GetMapping("/list")
    public String listServicio(Model model){
        System.out.println("++ Entrando a la lista de servicios ++++++++++++++");
        Page<Servicio> page = servicioService.getPage(0);
        // List<Servicio> servicios = page.stream().toList();
        List<Servicio> servicios = servicioRepository.findAll();
        // List<Integer> pageSequence = pageSequence(0, page.getTotalPages(), 20 );
        List<Integer> pageSequence = pageSequence(0, 1, 20 );
        CurrentObject currentObject = new CurrentObject();

        model.addAttribute("servicios", servicios);
        model.addAttribute("pages", pageSequence);
        model.addAttribute("current", 0);
        model.addAttribute("pageSequence", pageSequence);
        model.addAttribute("currentObject", currentObject);
        return "/Servicio/List";
    }

    @PostMapping(value =  "/gopage", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String gopage(CurrentObject currentObject, Model model){
        System.out.println("Current de gopage:" + currentObject.current + " descripcion:" + currentObject.descripcion);
        Integer pg = currentObject.current;

        Page<Servicio> page ;
        if(currentObject.descripcion.isBlank() || currentObject.descripcion.isEmpty()) {
            page = servicioService.getPage(pg);
        } else {
            page = servicioService.getPage(currentObject.descripcion.toUpperCase(), currentObject.current);
        }
        List<Servicio> servicios = page.stream().toList();
        List<Integer> pageSequence = pageSequence(pg, page.getTotalPages(), 20 );
        // CurrentObject currentObject = new CurrentObject();

        model.addAttribute("servicios", servicios);
        model.addAttribute("pages", page.getTotalPages());
        model.addAttribute("current", pg);
        model.addAttribute("pageSequence", pageSequence);
        model.addAttribute("currentObject", currentObject);
        return "/Servicio/List";
    }

    @GetMapping("/listpage")
    public String listPage( @RequestParam("pg") Integer pg, @RequestParam("pages") Integer pages, Model model){
        Page<Servicio> page = servicioService.getPage(pg);
        List<Servicio> servicios = page.stream().toList();
        List<Integer> pageSequence = pageSequence(pg, page.getTotalPages(), 20 );
        CurrentObject currentObject = new CurrentObject();

        model.addAttribute("servicios", servicios);
        model.addAttribute("pages", page.getTotalPages());
        model.addAttribute("current", pg);
        model.addAttribute("pageSequence", pageSequence);
        model.addAttribute("currentObject", currentObject);
        return "/Servicio/List";
    }

    @GetMapping("/next")
    public String next(@RequestParam("pg") Integer pg, @RequestParam("pages") Integer pages, Model model){
        if(pg < pages){
            pg += 1;
        }

        Page<Servicio> page = servicioService.getPage(pg);
        List<Servicio> servicios = page.stream().toList();
        CurrentObject currentObject = new CurrentObject();

        List<Integer> pageSequence = pageSequence(pg, page.getTotalPages(), 20 );
        model.addAttribute("servicios", servicios);
        model.addAttribute("pages", page.getTotalPages());
        model.addAttribute("current", pg);
        model.addAttribute("pageSequence", pageSequence);
        model.addAttribute("currentObject", currentObject);
        return "/Servicio/List";
    }

    @GetMapping("/prev")
    public String prev(@RequestParam("pg") Integer pg, @RequestParam("pages") Integer pages, Model model){
        if (pg > 0){
            pg -= 1;
        }
        Page<Servicio> page = servicioService.getPage(pg);
        List<Servicio> servicios = page.stream().toList();
        CurrentObject currentObject = new CurrentObject();

        List<Integer> pageSequence = pageSequence(pg, page.getTotalPages(), 20 );
        model.addAttribute("servicios", servicios);
        model.addAttribute("pages", page.getTotalPages());
        model.addAttribute("current", pg);
        model.addAttribute("pageSequence", pageSequence);
        model.addAttribute("currentObject", currentObject);
        return "/Servicio/List";
    }

    @GetMapping("/view")
    public String viewServicio(Model model){
        return "/Servicio/View";
    }

    @GetMapping("/edit")
    public String editServicio(@RequestParam Long id, Model model){
        Servicio servicio = servicioRepository.getReferenceById(id);
        model.addAttribute("servicio", servicio);
        return "Servicio/Edit";
    }

    @GetMapping("/new")
    public String newServicio(Model model){
        Servicio servicio = new Servicio();
        model.addAttribute("servicio", servicio);
        return "/Servicio/Edit";
    }



    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveServicio(@Valid Servicio servicio, BindingResult result, Model model){

        var mod = result.getModel();
        if(result.hasErrors()){
            System.out.println("++ Hay errores en el servicio ++++++++++++++");
            if (!mod.isEmpty()) {
                mod.forEach((String k, Object obj) -> {
                    System.out.println("Error key: " + k + " Obj:" + obj);
                    System.out.println("++++++++++++++++++++++++");
                });
            }

            model.addAttribute("servicio", servicio);
            return "/servicio/Edit";
        } else {
            System.out.println("Inicio del save");
            System.out.println("........ save: servicio id:"+ servicio.getId());
            System.out.println("........ save: servicio clave:"+ servicio.getClave());
            System.out.println("........ save: servicio desc:"+ servicio.getDescripcion());
            System.out.println("........ save: servicio cveSat:"+ servicio.getCveSat());
            System.out.println("........ save: servicio costo:"+ servicio.getCosto());
            System.out.println("........ save: servicio precioVta:"+ servicio.getPrecioVta());
            System.out.println("........ save: servicio precioInt:"+ servicio.getPrecioInt());
            /*
            String clave = servicio.getClave().toUpperCase();
            String descripcion = servicio.getDescripcion().toUpperCase();
            String claveSat = servicio.getCveSat().toUpperCase();
            servicio.setClave(clave);
            servicio.setDescripcion(descripcion);
            servicio.setCveSat(claveSat);
            */

            servicioRepository.save(servicio);
            return "redirect:/servicio/list";

        }
    }





    @PostMapping("/save")
    public String saveServicio(Model model){return "/Servicio/List";
    }

    private List<Integer> pageSequence(Integer current, Integer pages, Integer size){
        List<Integer> sequence = new ArrayList<>();

        for(int i = current; i < pages; i++){
            sequence.add(i);
        }
        return sequence;
    }



class CurrentObject {
    int current;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    String descripcion;

    public CurrentObject() {
    }

    public void setCurrent(int current){
        this.current = current;
    }

    public int getCurrent(){
        return current;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrentObject that = (CurrentObject) o;
        return getCurrent() == that.getCurrent() && Objects.equals(getDescripcion(), that.getDescripcion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurrent(), getDescripcion());
    }
}

}
