/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.dto.ComentarioParaBeneficiarioDto;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.ComentarioEntity;
import com.tecnara.manoamiga.act.entities.MonitorEntity;
import com.tecnara.manoamiga.act.entities.ProfesorEntity;
import com.tecnara.manoamiga.act.entities.TutorEntity;
import com.tecnara.manoamiga.act.repositories.ITutorRepository;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/profesor/tutor")
public class ProfesorTutorController extends TecnaraBaseController {

    @Autowired
    private ITutorRepository repoTutor;

    @GetMapping("/buscador_id")
    @ResponseBody

    
    public String buscador(Long id) {
        TutorEntity x = repoTutor.findById(id).orElse(new TutorEntity());
        return x.getNombre();
    }
}
