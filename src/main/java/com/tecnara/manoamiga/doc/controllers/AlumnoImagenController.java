
package com.tecnara.manoamiga.doc.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.entities.FicheroEntity;
import com.tecnara.manoamiga.doc.repositories.IFicherosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/doc/alumno/imagen")
public class AlumnoImagenController extends TecnaraBaseController{
  @Autowired
private IFicherosRepository repofichero;

@GetMapping (value = "/descargar",
        
        produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
public @ResponseBody
byte []descargar(Long id){
    FicheroEntity file = repofichero.findById(id).get();
    byte[] fichero = file.getFichero();
    return fichero;
}
}
