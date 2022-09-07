/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ActController extends TecnaraBaseController{
    
    @Autowired
    private IGeneral general;
    
    @GetMapping("/act/index")
    public String index(){
        String rol=general.getRole();
        if (rol.startsWith("ROLE_ACT_Beneficiario")){
            return "redirect:/act/beneficiario/index";
        }
        if (rol.startsWith("ROLE_ACT_Monitor")){
            return "redirect:/act/monitor/index";
        }
        if (rol.startsWith("ROLE_ACT_Tutor")){
            return "redirect:/act/tutor/index";
        }
        if (rol.startsWith("ROLE_ACT_Admin")){
            return "redirect:/act/admin/index";
        }
        if (rol.startsWith("ROLE_ACT_Profesor")){
            return "redirect:/act/profesor/index";
        }
        return "redirect:/act/public/index";
    }
    
    @GetMapping("/act/logout")
    public String logout(){
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:index";
    }

}
