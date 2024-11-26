package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.repository.CotizacionRepository;
import com.evoltech.ciqapm.repository.EmpConahcytRepository;
import com.evoltech.ciqapm.repository.EmpleadoRepository;
import com.evoltech.ciqapm.repository.ProyectoRepository;
import com.evoltech.ciqapm.repository.datos.ConahcytRepository;
import com.evoltech.ciqapm.utils.BreadcrumbService;
import com.evoltech.ciqapm.utils.CotizaEmpleadoWrapper;
import com.evoltech.ciqapm.utils.EmpConahcytWrapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("empConahcyt")
public class EmpConahcytController {

    @Autowired
    EmpleadoRepository empleadoRepository;
    @Autowired
    ConahcytRepository conahcytRepository;
    @Autowired
    EmpConahcytRepository empConahcytRepository;
    private final ProyectoRepository proyectoRepository;

    public EmpConahcytController(EmpleadoRepository empleadoRepository,
                                    ConahcytRepository conahcytRepository,
                                    EmpConahcytRepository empConahcytRepository,
                                 ProyectoRepository proyectoRepository) {
        this.empleadoRepository = empleadoRepository;
        this.conahcytRepository = conahcytRepository;
        this.empConahcytRepository = empConahcytRepository;
        this.proyectoRepository = proyectoRepository;
    }

    @GetMapping("/list")
    private String listaEmpConahcyt(@RequestParam("id") Long id, Model model){
        Conahcyt conahcyt = conahcytRepository.getReferenceById(id);

        List<EmpConahcyt> empConahcyts = empConahcytRepository.findByConahcyt(conahcyt);
        System.out.println("Dentro de listEmpConahcyt:" + empConahcyts.size() );
        System.out.println("EmpConahcyts:" + empConahcyts.size());
        model.addAttribute("conahcytId", id);
        model.addAttribute("cotizaEmpleados", empConahcyts);
        return "EmpConahcyt/List";
    }

    @GetMapping("/new")
    private String nuevo(@RequestParam("id")Long id, Model model ){

        return "EmpConahcyt/Edit";
    }

    @GetMapping("/searchForm")
    private String SearchForm(@RequestParam("id") Long id, HttpSession session, Model model) {
        session.setAttribute("cotizacionId", id);
        Conahcyt proyecto = conahcytRepository.getReferenceById(id);
        BreadcrumbService breadcrumbService = new BreadcrumbService();
        String pathTipoProyecto = breadcrumbService.getPathTipoProyecto(proyecto);
        String pathProyecto = breadcrumbService.getPathProyecto(proyecto);
        String tagTipoProyecto = breadcrumbService.getTagTipoProyecto(proyecto);
        String proyectoNombre = proyecto.getNombre();
        model.addAttribute("pathTipoProyecto", pathTipoProyecto);
        model.addAttribute("pathProyecto", pathProyecto);
        model.addAttribute("tagTipoProyecto", tagTipoProyecto);
        model.addAttribute("proyectoNombre", proyectoNombre);
        return "EmpConahcyt/Search";
    }

    @PostMapping("/search")
    public String lookup(String search, Model model) {
        System.out.println("Searching...");
        List<Empleado> personal = empleadoRepository.findByNombreContainsIgnoreCase(search);
        System.out.println("Servicios size:" + personal.size());

        // servicios.stream().map(servicio -> servicio.getDescripcion()).forEach(System.out::println);
        model.addAttribute("personal", personal);

        return "EmpConahcyt/Result :: result_table";
    }

    @GetMapping("/add")
    public String addEmpleado(@RequestParam("id") Long id, HttpSession session, Model model){
        Set<Empleado> set = new HashSet<>();

        List<Empleado> listaPrevia = (List<Empleado>)session.getAttribute("empConahcyt");
        if(listaPrevia != null) {
            listaPrevia.stream().forEach(set::add);
        }

        Empleado personal = empleadoRepository.getReferenceById(id);

        if (personal != null){
            set.add(personal);
        }
        List<Empleado> lista = set.stream().toList();

        session.setAttribute("empConahcyt", lista);
        model.addAttribute("personal", lista);

        return "EmpConahcyt/Result :: list";
    }

    @GetMapping("/getarray")
    public String getarray(HttpSession session, Model model){
        CotizaEmpleadoWrapper cotizaEmpleadoWrapper = new CotizaEmpleadoWrapper();

        List<Empleado> listaPrevia = (List<Empleado>) session.getAttribute("empConahcyt");
        if(listaPrevia != null){
            listaPrevia.stream().forEach(p -> {
                CotizaEmpleado cs = new CotizaEmpleado();
                cs.setEmpleado(p);
                cs.setHoras(1);
                cotizaEmpleadoWrapper.getEmpleados().add(cs);
            });
        }
        Long id = (Long) session.getAttribute("cotizacionId");

        Conahcyt proyecto = conahcytRepository.getReferenceById(id);
        BreadcrumbService breadcrumbService = new BreadcrumbService();
        String pathTipoProyecto = breadcrumbService.getPathTipoProyecto(proyecto);
        String pathProyecto = breadcrumbService.getPathProyecto(proyecto);
        String tagTipoProyecto = breadcrumbService.getTagTipoProyecto(proyecto);
        String proyectoNombre = proyecto.getNombre();
        model.addAttribute("pathTipoProyecto", pathTipoProyecto);
        model.addAttribute("pathProyecto", pathProyecto);
        model.addAttribute("tagTipoProyecto", tagTipoProyecto);
        model.addAttribute("proyectoNombre", proyectoNombre);
        model.addAttribute("wrapper", cotizaEmpleadoWrapper);
        return "EmpConahcyt/Summary";
    }

    @PostMapping("/postarray")
    public String postArray(EmpConahcytWrapper personalWrapper,
                            HttpSession session, Model model){
        session.removeAttribute("empConahcyt");
        Long id = (Long) session.getAttribute("cotizacionId");
        System.out.println("Cotizacion ID:" + id);
        Conahcyt conahcyt = conahcytRepository.getReferenceById(id);

        List<EmpConahcyt> cotizaEmpleados = personalWrapper.getEmpleados();

        cotizaEmpleados.stream().forEach(co -> co.setConahcyt(conahcyt));

        cotizaEmpleados.stream().map(EmpConahcyt::getConahcyt).forEach(c -> System.out.println(c.getNombre()));

        cotizaEmpleados.stream().forEach(co -> empConahcytRepository.save(co));


        model.addAttribute("wrapper", personalWrapper);
        model.addAttribute("cotizacionId", id);
        return "redirect:/conahcyt/view/" + id;
    }

    @GetMapping("view")
    private String viewCotizaEmpleado(@RequestParam Long id, Model model){
        EmpConahcyt cotizaEmpleado = empConahcytRepository.getReferenceById(id);

        model.addAttribute("cotizaEmpleado", cotizaEmpleado);

        return "EmpConahcyt/View";
    }

}
