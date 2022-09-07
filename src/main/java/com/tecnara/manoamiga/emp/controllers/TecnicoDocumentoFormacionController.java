/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.emp.entities.DocumentoFormacionEntity;
import com.tecnara.manoamiga.emp.entities.FicheroEntity;
import com.tecnara.manoamiga.emp.repositories.IDocumentoFormacionRepository;
import com.tecnara.manoamiga.emp.repositories.IFicheroRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/emp/tecnico/documento_formacion")
public class TecnicoDocumentoFormacionController extends TecnaraBaseController {

    @Autowired
    private IDocumentoFormacionRepository repoDocuform;
    
    @Autowired
    private IFicheroRepository repoFichero;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new DocumentoFormacionEntity());
        } else {
            m.addAttribute("registro", repoDocuform.findById(id).get());
        }
        return "emp/tecnico/documento_formacion_form";
    }

    @PostMapping("guardar")
    public String guardar(DocumentoFormacionEntity registro, MultipartFile fichero) {
        if (fichero != null && fichero.isEmpty() == false) {
            try {
                FicheroEntity nueva = new FicheroEntity();
                nueva.setFichero(fichero.getBytes());
                nueva.setNombre(fichero.getOriginalFilename());
                FicheroEntity guardado = repoFichero.save(nueva);
                Long id = guardado.getId();
                registro.setIdFichero(id);
            } 
            catch (IOException e) {
                e.printStackTrace();
                return "redirect:form";
            }
       
      }

       repoDocuform.save(registro);
        return "redirect:form";
        
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoDocuform.deleteById(id);
        return "redirect:form";

    }

}
        
