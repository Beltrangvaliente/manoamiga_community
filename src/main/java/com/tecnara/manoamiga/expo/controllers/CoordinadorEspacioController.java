package com.tecnara.manoamiga.expo.controllers;


import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;

import com.tecnara.manoamiga.expo.entities.AreaEntity;

import java.util.List;

import com.tecnara.manoamiga.expo.entities.EspacioEntity;
import com.tecnara.manoamiga.expo.entities.EventoEntity;
import com.tecnara.manoamiga.expo.repositories.IEspacioRepository;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/expo/coordinador/espacio")
public class CoordinadorEspacioController extends TecnaraBaseController{

    @Autowired
    private IEspacioRepository repoEspacio;

    @GetMapping("/list")
    public String list(Model m) {

        return "expo/coordinador/espacio_list";
    }
    
    @GetMapping("/list_data")
    @ResponseBody // para que devuelva un JSON
    public List<EspacioEntity> listData() {
        return repoEspacio.findAll();
    }

    @GetMapping("/form")
    //@RequestMapping(path = "/form", method = {RequestMethod.GET, RequestMethod.POST})
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new EspacioEntity());
        } else {
            m.addAttribute("registro", repoEspacio.findById(id).get());
        }
        return "expo/coordinador/espacio_form";
    }

    @PostMapping("/guardar")
    public String guardar(EspacioEntity registro) {
        repoEspacio.save(registro);
        return "redirect:list";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoEspacio.deleteById(id);
        return "redirect:list";
    }

     @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) { 
        return repoEspacio.findByUbicacionContainingIgnoreCase(filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getUbicacion()))
                .collect(Collectors.toList());
    }
    
        @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        EspacioEntity x = repoEspacio.findById(id).orElse(new EspacioEntity());
        return x.getUbicacion();
    }   
    
    

   

   

}
