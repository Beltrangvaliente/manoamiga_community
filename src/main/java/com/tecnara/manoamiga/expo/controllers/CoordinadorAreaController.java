
package com.tecnara.manoamiga.expo.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.expo.dto.TrabajadorDto;
import com.tecnara.manoamiga.expo.entities.AreaEntity;
import com.tecnara.manoamiga.expo.entities.TrabajadorEntity;
import com.tecnara.manoamiga.expo.repositories.IAreaRepository;
import com.tecnara.manoamiga.expo.repositories.ITrabajadorRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/expo/coordinador/area")
public class CoordinadorAreaController extends TecnaraBaseController{
    
    @Autowired
    private IAreaRepository repoArea;
    
    @Autowired
    private ITrabajadorRepository repoTrabajador;
    
    @GetMapping("/list")
    public String list(Model m) {
        return "expo/coordinador/area_list";
    }
    
    @GetMapping("/list_data")
    @ResponseBody // para que devuelva un JSON
    public List<AreaEntity> listData() {
        return repoArea.findAll(); // nos devuelve todos los datos de la tabla; sin filtrar
    }    
   
    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id==null){
            m.addAttribute("registro", new AreaEntity());
        }else{
            m.addAttribute("registro", repoArea.findById(id).get());
        }
        return "expo/coordinador/area_form";
    }    
    
    @PostMapping("/guardar")
    public String guardar(AreaEntity registro) {
        repoArea.save(registro);
        return "redirect:list";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoArea.deleteById(id);
        return "redirect:list";
    }    
    
    
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
