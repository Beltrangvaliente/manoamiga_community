
package com.tecnara.manoamiga.aaa.controllers;

import com.tecnara.manoamiga.aaa.services.IGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PrincipalController extends TecnaraBaseController{
    
    @Value(value="${tecnara.entorno}")
    private String entorno;
    
    @Value(value="${tecnara.version}")
    private String version;
    
    @Autowired
    private IGeneral general;

    @GetMapping("/public/info")
    @ResponseBody
    public String info2(){
        return entorno + "-" + version;
    }
    
    @GetMapping("/")
    public String index() {
        String rol=general.getRole();
        int modules=general.getModulesInstalled();
        if (rol.startsWith("ROLE_ACT_") || (modules==1 && general.isModule("act"))){
            return "redirect:/act/index";
        }
        if (rol.startsWith("ROLE_EXPO_") || (modules==1 && general.isModule("expo"))){
            return "redirect:/expo/index";
        }
        if (rol.startsWith("ROLE_EMP_") || (modules==1 && general.isModule("emp"))){
            return "redirect:/emp/index";
        }
        if (rol.startsWith("ROLE_DOC_") || (modules==1 && general.isModule("doc"))){
            return "redirect:/doc/index";
        }
        if (modules==1 && general.isModule("act")){
            return "redirect:/act/index";
        }
        if (modules==1 && general.isModule("expo")){
            return "redirect:/expo/index";
        }
        if (modules==1 && general.isModule("emp")){
            return "redirect:/emp/index";
        }   
        if (modules==1 && general.isModule("doc")){
            return "redirect:/doc/index";
        }         
        return "aaa/index";
    }
    
    @GetMapping("/index")
    public String index2() {
         
        return "aaa/index";
    }
    
    @GetMapping("/init")
    public String init() {
        return "aaa/init";
    }

    
}
