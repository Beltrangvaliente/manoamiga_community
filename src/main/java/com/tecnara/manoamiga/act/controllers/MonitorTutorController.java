package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.dto.TutorDto;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.TutorEntity;
import com.tecnara.manoamiga.act.repositories.ITutorRepository;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@Controller
@RequestMapping("/act/monitor/tutor")
public class MonitorTutorController extends TecnaraBaseController {
    
    
    @Autowired
    private IGeneral general;

    public MonitorTutorController(IGeneral general, ITutorRepository repoTutor) {
        this.general = general;
        this.repoTutor = repoTutor;
    }
    
    @Autowired
    private ITutorRepository repoTutor;

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoTutor.findByNombreContainingIgnoreCase(filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(),general.concat(x.getNombre(),x.getApellido())))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        TutorEntity x = repoTutor.findById(id).orElse(new TutorEntity());
        return general.concat(x.getNombre(),x.getApellido());
    }

}
