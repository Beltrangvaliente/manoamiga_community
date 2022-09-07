package com.tecnara.manoamiga.doc.controllers;

//import com.tecnara.manoamiga.doc.services.IEmulationService;
import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("doc_public_controller")
@RequestMapping("/doc/public")
public class PublicController extends TecnaraBaseController{
    
    //@Autowired
    //private IEmulationService emul;
    
    @GetMapping("/index")
    public String index(){
        //emul.generarDatosDePrueba();
        System.out.println("Entra");
        return "doc/public/index";
    }
    

    
}
