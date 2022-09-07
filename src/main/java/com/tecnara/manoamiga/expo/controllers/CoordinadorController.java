package com.tecnara.manoamiga.expo.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/expo/coordinador")
public class CoordinadorController extends TecnaraBaseController{
    
    @GetMapping("/index")
    public String index(Model m){
        return "expo/coordinador/index";
    }
    @GetMapping("/prueba")
    public String prueba(Model m){
        return "expo/coordinador/prueba";
    }
    
    
}
