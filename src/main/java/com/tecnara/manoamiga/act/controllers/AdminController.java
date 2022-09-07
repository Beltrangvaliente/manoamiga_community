 package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.entities.ActividadEntity;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.act.repositories.IBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IMonitorRepository;
import com.tecnara.manoamiga.act.repositories.ITutorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/admin")
public class AdminController extends TecnaraBaseController{

    @Autowired
    private IBeneficiarioRepository repoBeneficiario;
    
    @Autowired
    private IMonitorRepository repoMonitor;

    @Autowired
    private ITutorRepository repoTutor;

    @GetMapping("/index")
    public String index(Model m) {
        m.addAttribute("nuevosBeneficiarios", repoBeneficiario.adminContarUsuariosPendientes());
        m.addAttribute("nuevosTutores", repoTutor.adminContarUsuariosPendientes());
        m.addAttribute("nuevosMonitores", repoMonitor.adminContarUsuariosPendientes());
        return "act/admin/index";
    }

}
