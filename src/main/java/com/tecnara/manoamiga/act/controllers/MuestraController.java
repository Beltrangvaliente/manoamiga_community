/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/act/muestra")
public class MuestraController extends TecnaraBaseController{
    
    
    @GetMapping("")
    public String index(){
        return "act/muestra/index";
    }
    
    @GetMapping("/tabs")
    public String tabs(){
        return "act/muestra/tabs";
    }
    
    @GetMapping("/table") 
    public String table(){
        return "act/muestra/table";
    }    
    
    @GetMapping("/qr") 
    public String qr(){
        return "act/muestra/qr";
    }       

    @GetMapping("/test") 
    public String test(String c){
        c=c.replace(".", "");
        c=c.replace("/", "");
        c=c.replace("\\", "");
        return "act/muestra/" + c;
    }       


}
