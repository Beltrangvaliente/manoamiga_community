package com.tecnara.manoamiga.doc.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("doc_muestra_controller")
@RequestMapping("/doc/muestra")
public class MuestraController extends TecnaraBaseController{
    
    
    @GetMapping("")
    public String index(){
        return "doc/muestra/index";
    }
    
    @GetMapping("/tabs")
    public String tabs(){
        return "doc/muestra/tabs";
    }
    
    @GetMapping("/table") 
    public String table(){
        return "doc/muestra/table";
    }    
    
    @GetMapping("/qr") 
    public String qr(){
        return "doc/muestra/qr";
    }       

    @GetMapping("/test") 
    public String test(String c){
        c=c.replace(".", "");
        c=c.replace("/", "");
        c=c.replace("\\", "");
        return "doc/muestra/" + c;
    }       


}
