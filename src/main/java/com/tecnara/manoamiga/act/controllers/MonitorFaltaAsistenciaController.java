package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.entities.FaltaAsistenciaEntity;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.repositories.IFaltaAsistenciaRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/monitor/falta_asistencia")
public class MonitorFaltaAsistenciaController extends TecnaraBaseController {

    @Autowired
    private IFaltaAsistenciaRepository repoFaltaAsistencia;

    @Autowired
    private IGeneral general;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id==null){
            m.addAttribute("registro", new FaltaAsistenciaEntity());    
        }else{
            m.addAttribute("registro", repoFaltaAsistencia.findById(id).get() );    
        }

        
        return "act/monitor/falta_asistencia_form";
    }

    @GetMapping("/list")
    public String list(Model m) {
        return "act/monitor/falta_asistencia_list";
    }

    @GetMapping("/help")
    public String help(Model m) {
        return "act/monitor/falta_asistencia_help";
    }

    @PostMapping("/guardar")
    public String guardar(FaltaAsistenciaEntity registro) {
        registro.setIdMonitor(general.getIdValidado()); 
        repoFaltaAsistencia.save(registro);
        return "redirect:form";//dn
    }

    @GetMapping("/borrar")
    public String borrar(Long id){
    repoFaltaAsistencia.deleteById(id);
    return "redirect:form";//dn
    
    }

}
