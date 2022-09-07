
package com.tecnara.manoamiga.doc.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.entities.FicheroEntity;
import com.tecnara.manoamiga.doc.repositories.IDocumentoSubidoFicheroRepository;
import com.tecnara.manoamiga.doc.repositories.IFicherosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/doc/admin/imagen/")
public class AdminImagenController extends TecnaraBaseController{
 
    @Autowired
    private IDocumentoSubidoFicheroRepository repoDocumentoSubido;
    
}
