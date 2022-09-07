package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.dto.MonitorDto;
import com.tecnara.manoamiga.act.entities.ParticipacionMonitorEntity;
import com.tecnara.manoamiga.act.repositories.IMonitorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;

import org.springframework.beans.factory.annotation.Autowired;

import com.tecnara.manoamiga.act.repositories.IMonitorRepository;
import com.tecnara.manoamiga.act.repositories.IParticipacionMonitorRepository;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/act/monitor/participacion_monitor")

public class MonitorParticipacionMonitorController extends TecnaraBaseController {

    @Autowired
    private IParticipacionMonitorRepository repoMonitor;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new ParticipacionMonitorEntity());
        } else {
            m.addAttribute("registro", repoMonitor.findById(id).get());
        }

        return "act/monitor/participacion_monitor_form";

    }

    @PostMapping("/guardar")
    public String guardar(ParticipacionMonitorEntity registro) {
        repoMonitor.save(registro);
        return "redirect:form";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoMonitor.deleteById(id);
        return "redirect:form";
    }

}
