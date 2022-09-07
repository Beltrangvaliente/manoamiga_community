package com.tecnara.manoamiga.doc.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/doc/public/listado_centros")
public class PublicListadoCentroController extends TecnaraBaseController{
    
    @GetMapping("/list")
    public String list() {
       return "doc/public/centro_list";
    }
    
}
