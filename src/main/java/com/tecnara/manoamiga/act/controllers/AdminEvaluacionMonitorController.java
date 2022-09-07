/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.entities.EvaluacionMonitorEntity;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.act.repositories.IEvaluacionMonitorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/admin/evaluacion_monitor")
public class AdminEvaluacionMonitorController extends TecnaraBaseController{

    @Autowired
    private IEvaluacionMonitorRepository repoEvaluacionMonitor;//DN
    
    @GetMapping("/form")
    public String form(Model m) {
        m.addAttribute("registro", new EvaluacionMonitorEntity());
        return "act/admin/evaluacion_monitor_form";
    }
    
    
    @GetMapping("/list")
    public String list(Model m) {
        return "act/admin/evaluacion_monitor_list";
    }
    @GetMapping("/list_data")
    @ResponseBody
    public List<EvaluacionMonitorEntity> listData(){
        return repoEvaluacionMonitor.findAll();// Nos devuelve los datos de la tabla sin filtrar.DN
    }
    
    @PostMapping("/guardar")
    public String guardar (EvaluacionMonitorEntity registro){
    repoEvaluacionMonitor.save(registro);
    return"redirect:list";
    
    }
    
    @GetMapping("/borrar")
    public String borrar(Long id){
    repoEvaluacionMonitor.deleteById(id);
    return "redirect:list";
        
    }

}
