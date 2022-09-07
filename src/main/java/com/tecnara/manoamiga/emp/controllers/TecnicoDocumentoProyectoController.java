
package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.emp.entities.DocumentoProyectoEntity;
import com.tecnara.manoamiga.emp.entities.FicheroEntity;
import com.tecnara.manoamiga.emp.entities.FormacionParticipanteEntity;
import com.tecnara.manoamiga.emp.repositories.IDocumentoProyectoRepository;
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
@RequestMapping("/emp/tecnico/documento_proyecto")
public class TecnicoDocumentoProyectoController extends TecnaraBaseController{
  
    @Autowired
    private IFicheroRepository repoFichero;
    
    @Autowired
    private IDocumentoProyectoRepository repoProyecto;
    
    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id==null) {
            m.addAttribute("registro", new DocumentoProyectoEntity());
        } else {
            m.addAttribute("registro", repoProyecto.findById(id).get());
        }    
        return"emp/tecnico/documentacion_form";
    }

    
    
    @PostMapping("guardar")
    public String guardar(DocumentoProyectoEntity registro, MultipartFile fichero) {
        if (fichero != null && fichero.isEmpty() == false) {
            try {
                FicheroEntity nueva = new FicheroEntity();
                nueva.setFichero(fichero.getBytes());
                nueva.setNombre(fichero.getOriginalFilename());
                FicheroEntity guardado = repoFichero.save(nueva);
                Long id = guardado.getId();
                registro.setIdFichero(id);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:form";
            }
        }

        repoProyecto.save(registro);
        return "redirect:form";
    }    
    
    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoProyecto.deleteById(id);
        return"redirect:list";
    }
    
}
