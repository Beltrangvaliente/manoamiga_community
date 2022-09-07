package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import com.tecnara.manoamiga.emp.entities.DocumentoParticipanteEntity;
import com.tecnara.manoamiga.emp.entities.FicheroEntity;
import com.tecnara.manoamiga.emp.entities.FormacionEntity;
import com.tecnara.manoamiga.emp.entities.FormacionParticipanteEntity;
import com.tecnara.manoamiga.emp.entities.FormacionPreviaEntity;
import com.tecnara.manoamiga.emp.repositories.IDocumentoParticipanteRepository;
import com.tecnara.manoamiga.emp.repositories.IFicheroRepository;
import com.tecnara.manoamiga.emp.repositories.IFormacionPreviaRepository;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/emp/tecnico/documento_participante")
public class TecnicoDocumentoParticipanteController extends TecnaraBaseController {

    @Autowired
    private IDocumentoParticipanteRepository repoDocumentoParticipante;

    @Autowired
    private IUtilidadesService util;

    @Autowired
    private IFicheroRepository repoFichero;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new DocumentoParticipanteEntity());
        } else {
            m.addAttribute("registro", repoDocumentoParticipante.findById(id).get());
        }
        return "emp/tecnico/documento_participante_form";

    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoDocumentoParticipante.deleteById(id);
        return "redirect:list";

    }

    @PostMapping("guardar")
    public String guardar(DocumentoParticipanteEntity registro, MultipartFile fichero) {
        if (fichero != null && fichero.isEmpty() == false) {
            try {
                FicheroEntity nuevo = new FicheroEntity();
                nuevo.setFichero(fichero.getBytes());
                nuevo.setNombre(fichero.getOriginalFilename());
                FicheroEntity guardado = repoFichero.save(nuevo);
                Long id = guardado.getId();
                registro.setIdFichero(id);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:form";
            }
        }
        
      repoDocumentoParticipante.save(registro);
        return "redirect:form";
                                
     }
        
    }
    
    
