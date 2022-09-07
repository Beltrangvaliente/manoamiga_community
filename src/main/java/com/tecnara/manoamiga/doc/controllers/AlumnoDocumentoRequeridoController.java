package com.tecnara.manoamiga.doc.controllers;


import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doc/alumno/documento_requerido")
public class AlumnoDocumentoRequeridoController extends TecnaraBaseController{
    
    @GetMapping("/list")
    public String list(){
        return "doc/alumno/documento_requerido_list";
    }    
    
}
