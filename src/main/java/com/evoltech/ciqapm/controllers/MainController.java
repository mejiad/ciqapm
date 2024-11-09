package com.evoltech.ciqapm.controllers;

import com.evoltech.ciqapm.model.*;
import com.evoltech.ciqapm.security.Usuario;
import com.evoltech.ciqapm.service.EtapaService;
import com.evoltech.ciqapm.service.EmpleadoServicio;
import com.evoltech.ciqapm.service.ProyectoServicio;
import com.evoltech.ciqapm.service.ServicioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller
public class MainController {


    @Autowired
    ServicioService servicioService;

    @Autowired
    EmpleadoServicio personalServicio;

    @Autowired
    EtapaService etapaService;

    @Autowired
    ProyectoServicio proyectoServicio;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public MainController(ServicioService servicioService, EmpleadoServicio personalServicio,
                          EtapaService etapaService,
                          ProyectoServicio proyectoServicio,
                          UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder){

        this.personalServicio = personalServicio;
        this.etapaService = etapaService;
        this.proyectoServicio = proyectoServicio;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String root(Model model) {
        return "home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        // Usuario user = new Usuario();
        String username = "";
        String password = "";
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/home";
    }


    private Usuario creaUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUsername("user1");
        usuario.setPassword(passwordEncoder.encode("pass"));
        usuario.setEmail("email@test.com");

        Usuario res = usuarioRepository.save(usuario);
        return res;
    }

}
