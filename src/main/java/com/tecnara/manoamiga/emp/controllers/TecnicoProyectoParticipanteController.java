package com.tecnara.manoamiga.emp.controllers;

import antlr.Utils;
import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import com.tecnara.manoamiga.emp.entities.ParticipanteEntity;
import com.tecnara.manoamiga.emp.entities.ProyectoEntity;
import com.tecnara.manoamiga.emp.entities.ProyectoParticipanteEntity;
import com.tecnara.manoamiga.emp.repositories.IParticipanteRepository;
import com.tecnara.manoamiga.emp.repositories.IProyectoParticipanteRepository;
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
@RequestMapping("emp/tecnico/proyecto_participante")
public class TecnicoProyectoParticipanteController extends TecnaraBaseController {

    @Autowired
    private IProyectoParticipanteRepository repoProyectoParticipante;
    
    @Autowired
    private IUtilidadesService utils;
    
    @Autowired
    private IParticipanteRepository repoParticipante;
    
    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new ProyectoParticipanteEntity());
        } else {
            m.addAttribute("registro", repoProyectoParticipante.findById(id).get());

        }
        return "emp/tecnico/proyecto_participante_form";
    }

    @PostMapping("/guardar")
    public String guardar(ProyectoParticipanteEntity registro) {
        repoProyectoParticipante.save(registro);
        return "redirect:form";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoProyectoParticipante.deleteById(id);
        return "redirect:form";

    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
       return repoParticipante.findByNombreContainingOrApellido1ContainingOrApellido2Containing(filtro, filtro, filtro)
                    .stream()
                    .map(x-> new ClaveValorDto(x.getId(),x.getNombre()))
                    .collect(Collectors.toList());
    }
    
    @GetMapping("/buscador_id")
    @ResponseBody
    public String  buscador(Long id) {
        ParticipanteEntity x=utils.findById(repoParticipante, id, ParticipanteEntity.class);
        return x.getNombre();
    }

}
