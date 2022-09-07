package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.entities.ParticipacionMonitorEntity;
import com.tecnara.manoamiga.act.repositories.IParticipacionMonitorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller 
@RequestMapping("/act/admin/participacion_monitor")
public class AdminParticipacionMonitorController extends TecnaraBaseController{
    
   @Autowired
    private IParticipacionMonitorRepository  repoParticipacionMonitor;
    
    @GetMapping("/form") 
    public String form(Model m, Long id) {
        if (id == null){
            m.addAttribute("registro", new ParticipacionMonitorEntity());
        }else{
            m.addAttribute("registro", repoParticipacionMonitor.findById(id).get());
        }
        return "act/admin/participacion_monitor_form";
    }
    
    @GetMapping("/list_data")
    @ResponseBody
    public List<ParticipacionMonitorEntity> listData() {
        return repoParticipacionMonitor.findAll();
    }
    
    @PostMapping("/guardar")
    public String guardar(ParticipacionMonitorEntity registro) {
        repoParticipacionMonitor.save(registro);
        return "redirect:form";

    }
        
    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoParticipacionMonitor.deleteById(id);
        return "redirect:form"; 
    }

}