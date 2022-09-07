package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.doc.controllers.*;
import com.tecnara.manoamiga.act.controllers.*;
import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.entities.PreferenciaEntity;
import com.tecnara.manoamiga.aaa.repositories.IPreferenciaRepository;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.doc.entities.CursoEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("emp/tecnico/preferencia")
public class TecnicoPreferenciaController extends TecnaraBaseController {

    @Autowired
    private IGeneral general;

    @Autowired
    private IPreferenciaRepository repoPrefe;

    @GetMapping("/list")
    public String list(Model m) {
        return "emp/tecnico/preferencias_list";
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData(String filtro) {
        return repoPrefe.preferList (filtro);
    }

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new PreferenciaEntity());
        } else {
            m.addAttribute("registro", repoPrefe.findById(id).get());
        }
        return "emp/tecnico/preferencias_form";
    }

    @PostMapping("/guardar")
    public String guardar(PreferenciaEntity registro) {
        repoPrefe.save(registro);
        return "redirect:list";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoPrefe.deleteById(id);
        return "redirect:list";
    }
}
