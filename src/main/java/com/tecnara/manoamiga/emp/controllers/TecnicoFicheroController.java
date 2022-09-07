package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.emp.entities.FicheroEntity;
import com.tecnara.manoamiga.emp.repositories.IFicheroRepository;
import com.tecnara.manoamiga.expo.entities.ImagenEntity;
import com.tecnara.manoamiga.expo.repositories.IImagenRepository;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/emp/tecnico/fichero")
public class TecnicoFicheroController {

    @Autowired
    private IFicheroRepository repoFichero;

    @GetMapping(value = "/descargar",
            produces = MediaType.ALL_VALUE)
    public @ResponseBody
    byte[] descargar(Long id, HttpServletResponse response) {
        FicheroEntity fichero = repoFichero.findById(id).get();
        byte[] datos = fichero.getFichero();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fichero.getNombre() + "\""); ;

        return datos;
    }
}
