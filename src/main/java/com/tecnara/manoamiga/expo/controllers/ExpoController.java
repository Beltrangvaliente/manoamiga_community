
package com.tecnara.manoamiga.expo.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/expo")
public class ExpoController extends TecnaraBaseController{
  
    
    @GetMapping("/index")
    public String index(){
        String rol=general.getRole();
        if (rol.startsWith("ROLE_EXPO_Coordinador")){
            return "redirect:/expo/coordinador/index";
        }
        if (rol.startsWith("ROLE_EXPO_Trabajador")){
            return "redirect:/expo/trabajador/index";
        }
        return "redirect:/expo/public/index";
    }
    
    @GetMapping("/logout")
    public String logout(){
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:index";
    }         
    
}
