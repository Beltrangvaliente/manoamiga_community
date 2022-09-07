package com.tecnara.manoamiga.emp.controllers;

import antlr.Utils;
import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.emp.entities.SectorEntity;
import com.tecnara.manoamiga.emp.repositories.ISectorRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/emp/tecnico/sector")
public class TecnicoSectorController extends TecnaraBaseController {

    @Autowired
    private ISectorRepository repoSector;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new SectorEntity());
        } else {
            m.addAttribute("registro", repoSector.findById(id).get());
        }
        return "emp/tecnico/sector_form";

    }

    @GetMapping("/list")
    public String List(Model m) {
        return "emp/tecnico/sector_list";
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<SectorEntity> listData() {
        return repoSector.findAll();
    }

    @PostMapping("/guardar")
    public String guardar(SectorEntity registro) {
        repoSector.save(registro);
        return "redirect:list";
    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoSector.findByNombreContainingIgnoreCase(filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getNombre()))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        SectorEntity x = repoSector.findById(id).get();
        return x.getNombre();
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoSector.deleteById(id);
        return "redirect:list";

    }
}
