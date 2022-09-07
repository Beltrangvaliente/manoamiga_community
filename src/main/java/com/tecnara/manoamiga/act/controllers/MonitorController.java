package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.entities.ActividadEntity;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.services.General;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/monitor")
public class MonitorController extends TecnaraBaseController{
    
  @Autowired
  private IGeneral General;
  
    @GetMapping("/index")
    public String index(Model m) {
       return "act/monitor/index";
    
    
    } 
    
}
