/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import com.tecnara.manoamiga.emp.entities.EmpresaEntity;
import com.tecnara.manoamiga.emp.entities.ExperienciaEntity;
import com.tecnara.manoamiga.emp.repositories.IExperienciaRepository;
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
@RequestMapping("/emp/tecnico/experiencia")

public class TecnicoExperienciaController extends TecnaraBaseController{
    
    @Autowired
    private IExperienciaRepository repoExperiencia;
    
    @Autowired
    private IUtilidadesService util;
    
    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new ExperienciaEntity());
        } else {
            m.addAttribute("registro", repoExperiencia.findById(id).get());
        }
        return "emp/tecnico/experiencia_form"; 
    
    
    }
    
    @PostMapping("guardar")
    public String guardar(ExperienciaEntity registro) {
        repoExperiencia.save(registro);
        return "redirect:list";
    }
    
    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoExperiencia.deleteById(id);
        return "redirect:list";
        
    }

}
    

