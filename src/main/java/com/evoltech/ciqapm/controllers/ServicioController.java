package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.Etapa;
import com.evoltech.ciqapm.model.Proyecto;
import com.evoltech.ciqapm.model.Servicio;
import com.evoltech.ciqapm.repository.ServicioRepository;
import com.evoltech.ciqapm.service.ServicioService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        Page<Servicio> page = servicioService.getPage(0);
        List<Servicio> servicios = page.stream().toList();
        List<Integer> pageSequence = pageSequence(0, page.getTotalPages(), 20 );
        CurrentObject currentObject = new CurrentObject();

        model.addAttribute("servicios", servicios);
        model.addAttribute("pages", page.getTotalPages());
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
    public String editEtapa(@RequestParam Long id, Model model){
        Servicio servicio = servicioRepository.getReferenceById(id);
        model.addAttribute("servicio", servicio);
        model.addAttribute("model", model);

        return "/Servicio/Edit";
    }

    @GetMapping("/new")
    public String newServicio(Model model){
        return "/Servicio/Edit";
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
