package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.ComentarioEntity;
import com.tecnara.manoamiga.act.entities.FaltaAsistenciaEntity;
import com.tecnara.manoamiga.act.repositories.IComentarioRepository;
import com.tecnara.manoamiga.act.repositories.IFaltaAsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/act/profesor/comentario")
public class ProfesorComentarioController extends TecnaraBaseController{
    
    @Autowired
    private IComentarioRepository repoComentario;

    @Autowired
    private IGeneral general;
    
    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id==null){
            m.addAttribute("registro", new ComentarioEntity());    
        }else{
            m.addAttribute("registro", repoComentario.findById(id).get());
        }
        
        return "act/profesor/comentario_form";
    }
    @PostMapping("/guardar")
    public String guardar(ComentarioEntity registro) {
        registro.setIdProfesor(general.getIdValidado());
        repoComentario.save(registro);
        return "redirect:form";
    }
    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoComentario.deleteById(id);
        return "redirect:form";
    } 
}
