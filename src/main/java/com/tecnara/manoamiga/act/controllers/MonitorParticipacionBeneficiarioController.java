package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.entities.ParticipacionBeneficiarioEntity;
import com.tecnara.manoamiga.act.repositories.IParticipacionBeneficiarioRepository;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/monitor/participacion_beneficiario")
public class MonitorParticipacionBeneficiarioController extends TecnaraBaseController {

    @Autowired
    private IParticipacionBeneficiarioRepository repoParticipacionBeneficiario;


    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new ParticipacionBeneficiarioEntity());
        } else {
            m.addAttribute("registro", repoParticipacionBeneficiario.findById(id).get());
        }

        return "act/monitor/participacion_beneficiario_form";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoParticipacionBeneficiario.deleteById(id);
        return "redirect:form";
    }

    @PostMapping("/guardar")
    public String guardar(ParticipacionBeneficiarioEntity registro) {
        repoParticipacionBeneficiario.save(registro);
        return "redirect:form";
    }

}
