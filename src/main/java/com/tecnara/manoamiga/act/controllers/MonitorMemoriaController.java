package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.entities.MemoriaEntity;
import com.tecnara.manoamiga.act.entities.ParticipacionMonitorEntity;
import com.tecnara.manoamiga.act.repositories.IMemoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/act/monitor/memoria")
public class MonitorMemoriaController extends TecnaraBaseController {

    @Autowired
    public IMemoriaRepository repoMemoria;

    @Autowired
    private IGeneral general;

    @GetMapping("/list")
    public String list(Model m) {
        return "act/monitor/memoria_list";
    }

    @PostMapping("/guardar")
    public String guardar(MemoriaEntity registro) {
        registro.setIdMonitor(general.getIdValidado());
        repoMemoria.save(registro);
        return "redirect:form";

    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoMemoria.deleteById(id);
        return "redirect:form";
    }

    @GetMapping("/form")
    public String form(Model m, Long id) {

        if (id == null) {

            m.addAttribute("registro", new MemoriaEntity());

        } else {

            m.addAttribute("registro", repoMemoria.findById(id).get());

        }

        return "act/monitor/memoria_form";

    }

}
