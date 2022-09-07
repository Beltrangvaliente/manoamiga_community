package com.tecnara.manoamiga.doc.controllers;


import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("doc_admin_controller")
@RequestMapping("/doc/admin")
public class AdminController extends TecnaraBaseController{
    
    @GetMapping("/index")
    public String index(Model m){
        return "doc/admin/index";
    }    
    
}
