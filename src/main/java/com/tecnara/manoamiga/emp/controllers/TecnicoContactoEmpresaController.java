
package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.emp.entities.ContactoEmpresaEntity;
import com.tecnara.manoamiga.emp.repositories.IContactoEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/emp/tecnico/contacto_empresa")
public class TecnicoContactoEmpresaController extends TecnaraBaseController{
    
    @Autowired
    private IContactoEmpresaRepository repoEmpresa;
    
    @GetMapping("/form")
    public String form(Model m, Long id) {
       if (id== null) {
           m.addAttribute("registro", new ContactoEmpresaEntity());
       } else {
           m.addAttribute("registro", repoEmpresa.findById(id).get());
       } 
       return"emp/tecnico/contacto_empresa_form";
    }
    
    @PostMapping("/guardar")
    public String guardar(ContactoEmpresaEntity registro) {
        repoEmpresa.save(registro);
        return "redirect:list";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoEmpresa.deleteById(id);
        return "redirect:list";
    }
}
