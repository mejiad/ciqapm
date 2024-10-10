import com.evoltech.ciqapm.model.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.evoltech.ciqapm.repository.ProyectoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("pago")
public class PagoCotroller {

    private ProyectoRepository proyectoRepository;

    public PagoCotroller(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    @GetMapping("/list")
    public String listPago(@RequestParam("id") Long id, Model model) {
        System.out.println("Numero de proyecto desde pago:" + id);
        Proyecto proyecto = proyectoRepository.getReferenceById(id);


        model.addAttribute("proyecto", proyecto);
        List<Pagos> pagos = pagoRepository.findByProyecto(proyecto);
        model.addAttribute("pagos", pagos);

        return "/Pagos/List";
    }


    @GetMapping("/view")
    public String viewPago(Model model){
        return "/Pagos/View";
    }

    @GetMapping("/edit")
    public String editPago(@RequestParam Long id, Model model){
        Pagos pago = pagosRepository.getReferenceById(id);
        List<PagosEstado> estados = List.of(PagosEstado.values());
        Proyecto proyecto = pago.getProyecto();
        model.addAttribute("pago", pago);
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("estados", estados);
        return "/Pagos/Edit";
    }

    @GetMapping("/new")
    public String newEtapa(@RequestParam Long id,  Model model) {
        Proyecto proyecto = proyectoRepository.getReferenceById(id);
        List<PagosEstado> estados = List.of(PagosEstado.values());
        Pagos pago = new Pagos();
        pago.setProyecto(proyecto);
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("estados", estados);
        return "/Pagos/Edit";
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String savePago(Pagos pago,  Model model){
        System.out.println("Valor de pago proyecto: " + pago.getProyecto().getNombre());
        System.out.println("fechaFacturacion: " + pago.getFechaFacturacion());
        System.out.println("estado: " + pago.getEstado());
        pagosRepository.save(pago);
        return "redirect:/proyecto/list";
    }


}
