/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.entities.ComentarioEntity;
import com.tecnara.manoamiga.act.repositories.IComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Tecnara
 *
 */
@Controller
@RequestMapping("/act/tutor/comentario")
public class TutorComentarioController extends TecnaraBaseController {

    
    @Autowired
    private IGeneral general;

    
    
    @GetMapping("/list")
    public String list(Model m) {
        return "act/monitor/comentario_list";
    }

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null){
            m.addAttribute("registro", new ComentarioEntity());  
        }else{
            m.addAttribute("registro", repoComentario.findById(id).get() );
        }
        return "act/tutor/comentario_form";
    }     

    @Autowired
    private IComentarioRepository repoComentario;
    

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoComentario.deleteById(id);
        return "redirect:list";
    }

    @PostMapping("/guardar")
    public String guardar(ComentarioEntity registro) {
        registro.setIdTutor(general.getIdValidado());
        registro.setFechaComentario(general.getFechaActual());
        registro.setHoraComentario(general.getHoraActual());
               
        repoComentario.save(registro);
        return "redirect:list";
    }

}
