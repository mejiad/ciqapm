package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.Cliente;
import com.evoltech.ciqapm.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("cliente")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/list")
    public String listCliente(Model model) {

        List<Cliente> clientes = clienteRepository.findAll();
        model.addAttribute("clientes", clientes);
        return "Cliente/List";
    }

    @GetMapping("/new")
    public String newCliente(Model model){
        Cliente cliente = new Cliente();

        model.addAttribute("cliente", cliente);

        return "Cliente/Edit";
    }

    @GetMapping("/edit")
    public String editCliente(@RequestParam("id") Long id, Model model) {
        Cliente cliente = clienteRepository.getReferenceById(id);

        model.addAttribute("cliente", cliente);
        return "Cliente/Edit";
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveCliente(@Valid Cliente cliente, BindingResult result, Model model) {

        var mod = result.getModel();
        if(result.hasErrors()){
            System.out.println("++ Hay errores en el cliente ++++++++++++++");
            if (!mod.isEmpty()) {
                mod.forEach((String k, Object obj) -> {
                    System.out.println("Error key: " + k + " Obj:" + obj);
                    System.out.println("++++++++++++++++++++++++");
                });
            }

            model.addAttribute("cliente", cliente);
            return "Cliente/Edit";
        } else {
            // TODO: Crear el directorio del id del proyecto
            System.out.println("Inicio del save");
            clienteRepository.save(cliente);
            return "redirect:/cliente/list";
        }
    }


}
