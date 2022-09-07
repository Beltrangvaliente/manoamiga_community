
package com.tecnara.manoamiga.expo.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.expo.entities.ImagenEntity;
import com.tecnara.manoamiga.expo.repositories.IImagenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/expo/public/imagen")
public class PublicImagenController extends TecnaraBaseController{
    
    @Autowired
    private IImagenRepository repoImagen;
    
    @GetMapping(value = "/descargar",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] descargar(Long id){
        ImagenEntity img=repoImagen.findById(id).get();
        byte[] datos=img.getImagen();
        return datos;
    }
    
}
