package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.emp.entities.FicheroEntity;
import com.tecnara.manoamiga.emp.repositories.IFicheroRepository;
import com.tecnara.manoamiga.expo.entities.ImagenEntity;
import com.tecnara.manoamiga.expo.repositories.IImagenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/emp/public/fichero")
public class PublicFicheroController {

    @Autowired
    private IFicheroRepository repoFichero;

    @GetMapping(value = "/descargar",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] descargar(Long id) {
        FicheroEntity img = repoFichero.findById(id).get();
        byte[] datos = img.getFichero();
        return datos;
    }
}
