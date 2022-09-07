/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecnara.manoamiga.doc.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doc")
public class DocController extends TecnaraBaseController{ 
    
    @GetMapping("/index")
    public String index(String url){
        String rol=general.getRole();
        if (rol.startsWith("ROLE_DOC_Alumno")){
            return "redirect:/doc/alumno/index";
        }
        if (rol.startsWith("ROLE_DOC_Profesor")){
            return "redirect:/doc/profesor/index";
        }
        if (rol.startsWith("ROLE_DOC_Admin")){
            return "redirect:/doc/admin/index";
        }
        return "doc/index"; 
    }
    
    @GetMapping("/logout")
    public String logout(){
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:index";
    }        
}
