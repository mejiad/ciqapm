package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.repository.EmpConahcytRepository;
import com.evoltech.ciqapm.repository.EmpIndustriaRepository;
import com.evoltech.ciqapm.repository.EmpleadoRepository;
import com.evoltech.ciqapm.repository.datos.ConahcytRepository;
import com.evoltech.ciqapm.repository.datos.IndustriaRepository;
import com.evoltech.ciqapm.utils.BreadcrumbService;
import com.evoltech.ciqapm.utils.CotizaEmpleadoWrapper;
import com.evoltech.ciqapm.utils.EmpConahcytWrapper;
import com.evoltech.ciqapm.utils.EmpIndustriaWrapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("empIndustria")
public class EmpIndustriaController {

    @Autowired
    EmpleadoRepository empleadoRepository;
    @Autowired
    IndustriaRepository industriaRepository;
    @Autowired
    EmpIndustriaRepository empIndustriaRepository;

    public EmpIndustriaController(EmpleadoRepository empleadoRepository,
                                  IndustriaRepository industriaRepository,
                                  EmpIndustriaRepository empIndustriaRepository) {
        this.empleadoRepository = empleadoRepository;
        this.industriaRepository = industriaRepository;
        this.empIndustriaRepository = empIndustriaRepository;
    }

    @GetMapping("/list")
    private String listaEmpIndustria(@RequestParam("id") Long id, Model model){
        Industria industria = industriaRepository.getReferenceById(id);

        List<EmpIndustria> empIndustrias = empIndustriaRepository.findByIndustria(industria);
        System.out.println("Dentro de listEmpIndustria:" + empIndustrias.size() );
        System.out.println("EmpIndustrias:" + empIndustrias.size());
        model.addAttribute("industriaId", id);
        model.addAttribute("cotizaEmpleados", empIndustrias);
        return "EmpIndustria/List";
    }

    @GetMapping("/new")
    private String nuevo(@RequestParam("id")Long id, Model model ){

        return "EmpIndustria/Edit";
    }

    @GetMapping("/searchForm")
    private String SearchForm(@RequestParam("id") Long id, HttpSession session, Model model) {
        session.setAttribute("industriaId", id);

        Industria proyecto = industriaRepository.getReferenceById(id);
        BreadcrumbService breadcrumbService = new BreadcrumbService();
        String pathTipoProyecto = breadcrumbService.getPathTipoProyecto(proyecto);
        String pathProyecto = breadcrumbService.getPathProyecto(proyecto);
        String tagTipoProyecto = breadcrumbService.getTagTipoProyecto(proyecto);
        String proyectoNombre = proyecto.getNombre();
        model.addAttribute("pathTipoProyecto", pathTipoProyecto);
        model.addAttribute("pathProyecto", pathProyecto);
        model.addAttribute("tagTipoProyecto", tagTipoProyecto);
        model.addAttribute("proyectoNombre", proyectoNombre);


        return "EmpIndustria/Search";
    }

    @PostMapping("/search")
    public String lookup(String search, Model model) {
        System.out.println("Searching Industria...");
        List<Empleado> personal = empleadoRepository.findByNombreContainsIgnoreCase(search);
        System.out.println("Servicios size:" + personal.size());

        // servicios.stream().map(servicio -> servicio.getDescripcion()).forEach(System.out::println);
        model.addAttribute("personal", personal);

        return "EmpIndustria/Result :: result_table";
    }

    @GetMapping("/add")
    public String addEmpleado(@RequestParam("id") Long id, HttpSession session, Model model){
        // RequestParam id es del empleado
        List<Empleado> lista = new ArrayList<>();

        List<Empleado> listaPrevia = (List<Empleado>)session.getAttribute("empIndustria");
        if(listaPrevia != null) {
            listaPrevia.stream().forEach(lista::add);
        }

        Empleado personal = empleadoRepository.getReferenceById(id);
        System.out.println("Personal Nombre: " + personal.getNombre());
        System.out.println("Personal categoria: " + personal.getCategoria());

        lista.add(personal);

        session.setAttribute("empIndustria", lista);

        model.addAttribute("personal", lista);

        return "EmpIndustria/Result :: list";
    }

    @GetMapping("/getarray")
    public String getarray(HttpSession session, Model model){
        EmpIndustriaWrapper empIndustriaWrapper = new EmpIndustriaWrapper();

        List<Empleado> listaPrevia = (List<Empleado>) session.getAttribute("empIndustria");
        if(listaPrevia != null){
            listaPrevia.stream().forEach(p -> {
                EmpIndustria cs = new EmpIndustria();
                cs.setEmpleado(p);
                cs.setHoras(1);
                empIndustriaWrapper.getEmpleados().add(cs);
            });
        }

        Long id = (Long) session.getAttribute("industriaId");

        Industria proyecto = industriaRepository.getReferenceById(id);
        BreadcrumbService breadcrumbService = new BreadcrumbService();
        String pathTipoProyecto = breadcrumbService.getPathTipoProyecto(proyecto);
        String pathProyecto = breadcrumbService.getPathProyecto(proyecto);
        String tagTipoProyecto = breadcrumbService.getTagTipoProyecto(proyecto);
        String proyectoNombre = proyecto.getNombre();
        model.addAttribute("pathTipoProyecto", pathTipoProyecto);
        model.addAttribute("pathProyecto", pathProyecto);
        model.addAttribute("tagTipoProyecto", tagTipoProyecto);
        model.addAttribute("proyectoNombre", proyectoNombre);
        model.addAttribute("wrapper", empIndustriaWrapper);
        return "EmpIndustria/Summary";
    }

    @PostMapping("/postarray")
    public String postArray(EmpIndustriaWrapper personalWrapper,
                            HttpSession session, Model model){
        session.removeAttribute("empIndustria");
        Long id = (Long) session.getAttribute("industriaId");
        System.out.println("Proyecto ID:" + id);
        Industria industria = industriaRepository.getReferenceById(id);

        List<EmpIndustria> cotizaEmpleados = personalWrapper.getEmpleados();

        cotizaEmpleados.stream().forEach(co -> co.setIndustria(industria));

        cotizaEmpleados.stream().map(EmpIndustria::getIndustria).forEach(c -> System.out.println(c.getNombre()));

        cotizaEmpleados.stream().forEach(co -> empIndustriaRepository.save(co));

        model.addAttribute("wrapper", personalWrapper);
        model.addAttribute("proyectoId", id);
        return "redirect:/industria/view/" + id;
    }

    @GetMapping("view")
    private String viewCotizaEmpleado(@RequestParam Long id, Model model){
        // id es la Industria
        EmpIndustria emp = empIndustriaRepository.getReferenceById(id);

        model.addAttribute("cotizaEmpleado", emp);

        return "EmpIndustria/View";
    }

}
