package com.tecnara.manoamiga.doc.controllers;

import antlr.Utils;
import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.entities.AlumnoEntity;
import com.tecnara.manoamiga.doc.repositories.IAlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doc/alumno")
public class AlumnoController extends TecnaraBaseController {

    @Autowired
    private IGeneral general;
    
    @GetMapping("/index")
    public String index(Model m) {
        return "doc/alumno/index";
    }

    @GetMapping("/perfil/form")
    public String form(Model m) {
        m.addAttribute("registro", new UsuarioEntity());
        return "doc/alumno/usuario_form";
    }


}
