package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/act/public")
public class PublicIndexController extends TecnaraBaseController{

    @Autowired
    private IGeneral general;
    
    @GetMapping("/index")
    public String index(Model m) {
        m.addAttribute("html", general.getPreferencia("public_html_index", "<h1 class='text-center'>Mano amiga</h1>"));
        return "act/public/index";
    }

}
