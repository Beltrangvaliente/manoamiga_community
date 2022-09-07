/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.expo.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.expo.entities.ImagenDecorativaEntity;
import com.tecnara.manoamiga.expo.entities.ImagenEntity;
import com.tecnara.manoamiga.expo.entities.ServicioEntity;
import com.tecnara.manoamiga.expo.repositories.IImagenDecorativaRepository;
import com.tecnara.manoamiga.expo.repositories.IImagenRepository;
import com.tecnara.manoamiga.expo.repositories.IServicioRepository;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/expo/coordinador/servicio")
public class CoordinadorServicioController extends TecnaraBaseController {

    @Autowired
    private IGeneral general;
    @Autowired
    private IServicioRepository repoServicio;
    @Autowired
    private IImagenRepository repoImagen;
    @Autowired
    private IImagenDecorativaRepository repoDecorativa;

    @GetMapping("/list")
    public String list(Model m) {
        return "expo/coordinador/servicio_list";

    }

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/form")
    public String form(Model m, Long id) {

        if (id == null) {
            m.addAttribute("registro", new ServicioEntity());
        } else {
            m.addAttribute("registro", repoServicio.findById(id).get());
        }
        return "expo/coordinador/servicio_form";

    }

    @PostMapping("/guardar")
    public String guardar(ServicioEntity registro, MultipartFile imagen, String img) {

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
        repoServicio.save(registro);
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

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoServicio.deleteById(id);
        return "redirect:list";
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData() {

        return repoServicio.leerListaCoordinador();
    }
}

