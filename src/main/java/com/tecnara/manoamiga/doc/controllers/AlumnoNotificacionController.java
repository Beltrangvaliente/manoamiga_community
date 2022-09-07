package com.tecnara.manoamiga.doc.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.entities.NotificacionEntity;
import com.tecnara.manoamiga.doc.repositories.INotificacionRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/doc/alumno/notificacion")
public class AlumnoNotificacionController extends TecnaraBaseController{
    
    @Autowired
    private IGeneral general;
    
    
    @Autowired
    private INotificacionRepository repoNotificacion;
    
    @GetMapping("/list")
    public String list(Model m, Long idAlumno) {
        m.addAttribute("registro",repoNotificacion.alumnoList(idAlumno));
        m.addAttribute("idAlumno",idAlumno);
        return "doc/alumno/notificacion_list";
    }   
    
    @GetMapping("/list_data_notificacion")
    @ResponseBody
    public List<Map> listadoNotificacion(){
        return repoNotificacion.alumnoList(general.getIdValidado());
    }
    
    @GetMapping("/marcar_leido")
    
    public String marcarLeido( ){
       repoNotificacion.marcarNotificaciones(general.getIdValidado());

        
        return "redirect:list";
    }
    
    @GetMapping("/marcar_check")
    public String marcarLeidoBoton(Long id) {
        repoNotificacion.alumnoNotificacionBoton(id, general.getIdValidado());

        return "redirect:list";

    }
                
               
    
}
      