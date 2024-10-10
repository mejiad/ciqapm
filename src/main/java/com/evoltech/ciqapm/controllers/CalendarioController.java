package com.evoltech.ciqapm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("calendario")
public class CalendarioController {

    @GetMapping("")
    public String indexd(){
        return "Calendario/index";
    }


}