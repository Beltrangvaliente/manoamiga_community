
package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import com.tecnara.manoamiga.emp.repositories.IProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/emp/public/proyecto")
public class PublicProyectoController extends TecnaraBaseController {
 
    
    @Autowired
    private IUtilidadesService utils;
    
    @Autowired
    private IProyectoRepository repoProyecto;
    
    @GetMapping("/list")
    public String list(Model m, String filtro) {
        m.addAttribute("lista", repoProyecto.publicListProyecto());
        m.addAttribute("filtro", filtro);
        return "emp/public/proyecto_list";
    }
    
    
        


}
