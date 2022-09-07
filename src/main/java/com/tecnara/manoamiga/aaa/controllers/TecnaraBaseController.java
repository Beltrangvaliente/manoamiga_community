package com.tecnara.manoamiga.aaa.controllers;

import com.tecnara.manoamiga.aaa.config.SessionInfo;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;


public class TecnaraBaseController {
    
    @Autowired
    public IGeneral general;
    
    @ModelAttribute("general")  //m.addAttribute("general", general);
    public IGeneral general(){
        return general;
    }
    
    @ModelAttribute("error")  //m.addAttribute("general", general);
    public String error(HttpServletRequest request){
        return request.getParameter("error");
    }

    @ModelAttribute("confirmacion")  //m.addAttribute("general", general);
    public String confirmacion(HttpServletRequest request){
        return request.getParameter("confirmacion");
    }
    
    @ModelAttribute("notificaciones")  //m.addAttribute("general", general); 
    public Integer notificaciones(){
        try{
            return general.getNotificaciones();    
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
        
    }    
    

}
