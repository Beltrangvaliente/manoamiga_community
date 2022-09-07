package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.entities.EvaluacionBeneficiarioEntity;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.entities.ActividadEntity;
import com.tecnara.manoamiga.act.repositories.IEvaluacionBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IMonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/act/monitor/evaluacion_beneficiario")

public class MonitorEvaluacionBeneficiarioController extends TecnaraBaseController {

    @Autowired
    private IEvaluacionBeneficiarioRepository repoMonitorEvaluacion;//DN

    @GetMapping("/list")
    public String list(Model m) {
        return "act/monitor/evaluacion_beneficiario_list";
    }

    @Autowired
    private IGeneral general;

    @PostMapping("/guardar")
    public String guardar(EvaluacionBeneficiarioEntity registro) {
        registro.setIdMonitor(general.getIdValidado());
        registro.setFechaDeEvaluacion(general.getFechaActual());
        //general.getHoraActual();

        repoMonitorEvaluacion.save(registro);
        return "redirect:form";

    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoMonitorEvaluacion.deleteById(id);
        return "redirect:form";
    }

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new EvaluacionBeneficiarioEntity());
        } else {
            m.addAttribute("registro", repoMonitorEvaluacion.findById(id).get());
        }
        return "act/monitor/evaluacion_beneficiario_form";
    }
    
}
