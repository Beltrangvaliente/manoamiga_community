/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import com.tecnara.manoamiga.emp.entities.InteresEntity;
import com.tecnara.manoamiga.emp.repositories.IInteresRepository;
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
@RequestMapping("/emp/tecnico/interes")
public class TecnicoInteresController extends TecnaraBaseController{
    
    @Autowired
    private IInteresRepository repoInteres;
    
    @Autowired
    private IUtilidadesService utils;
    
    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new InteresEntity());
        } else {
            m.addAttribute("registro", repoInteres.findById(id).get());
        }
        return "emp/tecnico/interes_form"; 
    
    
    }
    
    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoInteres.deleteById(id);
        return "redirect:list";
        
    }
    
    @PostMapping("guardar")
    public String guardar(InteresEntity registro) {
        repoInteres.save(registro);
        return "redirect:list";
    }
    
}
