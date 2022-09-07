
package com.tecnara.manoamiga.expo.controllers;

import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.expo.entities.AreaEntity;
import com.tecnara.manoamiga.expo.entities.EspacioEntity;
import com.tecnara.manoamiga.expo.repositories.IAreaRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/expo/trabajador/area")
public class TrabajadorAreaController {
    
    @Autowired
     private IAreaRepository repoArea;
    
     @GetMapping("/buscador")
     @ResponseBody
     public List<ClaveValorDto> buscador(String filtro) {
        return repoArea.findByAreaContainingIgnoreCase(filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getArea()))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        AreaEntity x = repoArea.findById(id).orElse(new AreaEntity());
        return x.getArea();
    }
    
    
}
