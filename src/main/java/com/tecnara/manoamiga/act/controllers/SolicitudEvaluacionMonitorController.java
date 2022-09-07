
package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.act.entities.ActividadEntity;
import com.tecnara.manoamiga.act.entities.SolicitudEvaluacionMonitorEntity;
import com.tecnara.manoamiga.act.repositories.IActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tecnara.manoamiga.act.repositories.ISolicitudEvaluacionMonitorRepository;

@Controller
@RequestMapping("act/monitor/solicitud_evaluacion")
public class SolicitudEvaluacionMonitorController {
    
    @Autowired
    private ISolicitudEvaluacionMonitorRepository repoSolicitudEvaluacion;
    
    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new SolicitudEvaluacionMonitorEntity());
        } else {
            m.addAttribute("registro", repoSolicitudEvaluacion.findById(id).get());
        }
        return "act/monitor/solicitud_evaluacion_form";
    }
    
    @PostMapping("/guardar")
    public String guardar(SolicitudEvaluacionMonitorEntity registro) {
        repoSolicitudEvaluacion.save(registro);
        return "redirect:list";
    }
    
    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoSolicitudEvaluacion.deleteById(id);
        return "redirect:list";
    }
   
}
