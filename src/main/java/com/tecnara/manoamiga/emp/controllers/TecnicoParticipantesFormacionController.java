/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.emp.entities.FormacionEntity;
import com.tecnara.manoamiga.emp.entities.ProyectoEntity;
import com.tecnara.manoamiga.emp.repositories.IFormacionRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/emp/tecnico/participantes_formacion")
public class TecnicoParticipantesFormacionController extends TecnaraBaseController{
    
    @Autowired
    private IFormacionRepository repoFormacion;
    
    @GetMapping("/form") 
    public String form(Model m, Long id) {
        Optional<FormacionEntity> formacion=repoFormacion.findById(id);
        if (formacion.isPresent()){
            m.addAttribute("nombre", formacion.get().getNombre());
        }
        
        m.addAttribute("idCurso", id);
        return "emp/tecnico/participantes_formacion_form";
    }
    
    
}
