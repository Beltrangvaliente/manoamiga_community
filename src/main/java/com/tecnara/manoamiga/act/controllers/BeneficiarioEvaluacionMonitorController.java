
package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.entities.ActividadEntity;
import com.tecnara.manoamiga.act.entities.SolicitudEvaluacionMonitorEntity;
import com.tecnara.manoamiga.act.repositories.IActividadRepository;
import com.tecnara.manoamiga.act.repositories.ISolicitudEvaluacionMonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/act/beneficiario/evaluacion_monitor")
public class BeneficiarioEvaluacionMonitorController extends TecnaraBaseController{
    
    
    @Autowired
    private ISolicitudEvaluacionMonitorRepository repoSolicitud;

    //id corresponde a el id de la solicitud
    @GetMapping("/form")
    public String form(Model m, Long id,Long idActividad) {

        m.addAttribute("registro", repoSolicitud.findById(id).get());
 
        return "act/beneficiario/evaluacion_monitor_form";
    }


    
//guardar
    
}
