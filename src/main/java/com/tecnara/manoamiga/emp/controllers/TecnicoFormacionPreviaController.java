/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import com.tecnara.manoamiga.emp.entities.FormacionEntity;
import com.tecnara.manoamiga.emp.entities.FormacionPreviaEntity;
import com.tecnara.manoamiga.emp.repositories.IFormacionPreviaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author tecnara
 */
@Controller
@RequestMapping("/emp/tecnico/formacion_previa")
public class TecnicoFormacionPreviaController extends TecnaraBaseController{
    
    @Autowired
    private IFormacionPreviaRepository repoFormacionPrevia;
    
    @Autowired
    private IUtilidadesService util;
    
    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new FormacionPreviaEntity());
        } else {
            m.addAttribute("registro", repoFormacionPrevia.findById(id).get());
        }
        return "emp/tecnico/formacion_previa_form"; 
        
    }
    
    @PostMapping("guardar")
    public String guardar(FormacionPreviaEntity registro) {
        repoFormacionPrevia.save(registro);
        return "redirect:list";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoFormacionPrevia.deleteById(id);
        return "redirect:list";
    
    
    }
    
    
    
}
