package com.tecnara.manoamiga.expo.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.expo.entities.PlantillaEntity;
import com.tecnara.manoamiga.expo.repositories.IPlantillaRepository;
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
@RequestMapping("/expo/trabajador/plantilla")

public class TrabajadorPlantillaController extends TecnaraBaseController {

    @Autowired
    private IPlantillaRepository repoPlantilla;

    @GetMapping("/list")
    public String list(Model m) {
        return "expo/trabajador/plantilla_list";
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<PlantillaEntity> listData() {
        return repoPlantilla.findAll();

    }

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new PlantillaEntity());

        } else {
            m.addAttribute("registro", repoPlantilla.findById(id).get());

        }
        return "expo/trabajador/plantilla_form";

    }

    @PostMapping("/guardar")
    public String guardar(PlantillaEntity registro) {
        repoPlantilla.save(registro);
        return "redirect:list";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoPlantilla.deleteById(id);
        return "redirect:list";
    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoPlantilla.findByNombreContainingIgnoreCase(filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getNombre() + "(" + x.getNumeroImagenes() + " imágenes y " + x.getNumeroTextos() + " textos)"))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        PlantillaEntity x = repoPlantilla.findById(id).orElse(new PlantillaEntity());
        return x.getNombre() + "(" + x.getNumeroImagenes() + " imágenes y " + x.getNumeroTextos() + " textos)";
    }

}
