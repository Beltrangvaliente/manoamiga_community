package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.entities.ActividadEntity;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.EvaluacionBeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.EvaluacionMonitorEntity;
import com.tecnara.manoamiga.act.entities.HorarioEntity;
import com.tecnara.manoamiga.act.entities.SolicitudEvaluacionMonitorEntity;
import com.tecnara.manoamiga.act.repositories.IActividadRepository;
import com.tecnara.manoamiga.act.repositories.IBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IEvaluacionMonitorRepository;
import com.tecnara.manoamiga.act.repositories.ISolicitudEvaluacionMonitorRepository;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.entities.NotificacionEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("act_profesor_controller")
@RequestMapping("/act/profesor")
public class ProfesorController extends TecnaraBaseController {

    @GetMapping("/index")
    public String list(Model m) {
        return "redirect:/act/profesor/beneficiario/list";  
    }

    
}
