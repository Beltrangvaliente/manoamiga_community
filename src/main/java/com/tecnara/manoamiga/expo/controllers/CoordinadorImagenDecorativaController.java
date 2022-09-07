package com.tecnara.manoamiga.expo.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.expo.entities.AspectoEntity;
import com.tecnara.manoamiga.expo.entities.ImagenDecorativaEntity;
import com.tecnara.manoamiga.expo.entities.ImagenEntity;
import com.tecnara.manoamiga.expo.repositories.IImagenDecorativaRepository;
import com.tecnara.manoamiga.expo.repositories.IImagenRepository;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/expo/coordinador/imagen_decorativa")

public class CoordinadorImagenDecorativaController extends TecnaraBaseController {

    @Autowired
    private IImagenDecorativaRepository repoDecorativa;

    @Autowired
    private IImagenRepository repoImagen;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new ImagenDecorativaEntity());

        } else {
            m.addAttribute("registro", repoDecorativa.findById(id).get());

        }
        return "expo/coordinador/imagen_decorativa_form";
    }

    @GetMapping("/list")
    public String list(Model m) {
        return "expo/coordinador/imagen_decorativa_list";

    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<ImagenDecorativaEntity> listData() {
        return repoDecorativa.findAll();
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoDecorativa.deleteById(id);
        return "redirect:list";
    }

    @PostMapping("/guardar")
    public String guardar(ImagenDecorativaEntity registro, MultipartFile imagen, String img) {
        if (imagen != null && imagen.isEmpty() == false) {
            try {
                ImagenEntity nueva = new ImagenEntity();
                nueva.setImagen(imagen.getBytes());
                ImagenEntity guardado = repoImagen.save(nueva);
                Long id = guardado.getId();
                registro.setIdImagen(id);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:form";
            }
        }
        if (img.length() > 10) {
            img = img.replace("data:image/jpeg;base64,", "");
            img = img.replace("data:image/png;base64,", "");
            ImagenEntity nueva = new ImagenEntity();
            nueva.setImagen(convertToImage(img));
            ImagenEntity guardado = repoImagen.save(nueva);
            repoImagen.flush();
            Long id = guardado.getId();
            registro.setIdImagen(id);
        }

        repoDecorativa.save(registro);
        return "redirect:list";

    }

    public byte[] convertToImage(String base64) {
        try {
            byte[] decodedString = Base64.getDecoder().decode(base64.getBytes("UTF-8"));
            return decodedString;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

}
