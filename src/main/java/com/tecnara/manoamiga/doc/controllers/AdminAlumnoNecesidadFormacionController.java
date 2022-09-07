/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.doc.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.entities.AlumnoEntity;
import com.tecnara.manoamiga.doc.entities.AlumnoNecesidadFormacionEntity;
import com.tecnara.manoamiga.doc.entities.NecesidadFormacionEntity;
import com.tecnara.manoamiga.doc.repositories.IAlumnoNecesidadFormacionRepository;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/doc/admin/alumno_necesidad_formacion")
public class AdminAlumnoNecesidadFormacionController extends TecnaraBaseController{ 
    @Autowired 
    private IAlumnoNecesidadFormacionRepository repoANF;
    @Autowired
    private IUtilidadesService utils;
    @Autowired
    private IGeneral general;
    
    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new AlumnoNecesidadFormacionEntity());
        } else {
            m.addAttribute("registro", repoANF.findById(id).get());
        }
        return "doc/admin/alumno_necesidad_formacion_form";
    }
    
    @PostMapping("/guardar")
    public String guardar(AlumnoNecesidadFormacionEntity registro) {
        repoANF.save(registro);
        return "redirect:list";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoANF.deleteById(id);
        return "redirect:list";
    } 
    
    
    
    
}
