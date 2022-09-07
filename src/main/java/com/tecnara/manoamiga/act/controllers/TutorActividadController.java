
package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.repositories.IActividadRepository;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("act/tutor/actividad")
public class TutorActividadController extends TecnaraBaseController{
    
    @Autowired
    private IActividadRepository repoActividad;
    
    @GetMapping("/list")
    public String List(Model m){
    return "act/tutor/actividad_list";
    }
    
    
    @GetMapping("/list_data")
    @ResponseBody
    public List<Map>listData(){
    return repoActividad.tutorList();
    }
}
