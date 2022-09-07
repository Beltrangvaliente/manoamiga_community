
package com.tecnara.manoamiga.expo.controllers;

import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.expo.entities.EspacioEntity;
import com.tecnara.manoamiga.expo.entities.TrabajadorEntity;
import com.tecnara.manoamiga.expo.repositories.IEspacioRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller 
@RequestMapping("/expo/trabajador/espacio")
public class TrabajadorEspacioController {

    @Autowired
    private IEspacioRepository repoEspacio;
    
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
