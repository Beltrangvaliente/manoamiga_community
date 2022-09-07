package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.repositories.IEmpresaRepository;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import com.tecnara.manoamiga.emp.entities.ContratoEntity;
import com.tecnara.manoamiga.emp.entities.EmpresaEntity;
import com.tecnara.manoamiga.emp.repositories.IContratoRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("emp_tecnico_contrato_controller")
@RequestMapping("/emp/tecnico/contrato")
public class TecnicoContratoController extends TecnaraBaseController {

    @Autowired
    private IUtilidadesService utils;
    @Autowired
    private IContratoRepository repoContrato;
    @Autowired
    private IEmpresaRepository repoEmpresa;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new ContratoEntity());
        } else {
            m.addAttribute("registro", repoContrato.findById(id));
        }
        return "emp/tecnico/contrato_form";
    }
    
    @GetMapping("/list")
    public String list(Model m){
    return "emp/tecnico/contrato_list";
    }
    
      @GetMapping("/formsinmenu")
    public String forsinmenu(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new ContratoEntity());
        } else {
            m.addAttribute("registro", repoContrato.findById(id).get());
        }
        m.addAttribute("sinmenu", true);
        return "emp/tecnico/contrato_form";
    }
    
      @PostMapping("/guardar")
    public String guardar(ContratoEntity registro) {
        repoContrato.save(registro);
        return "redirect:list";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoContrato.deleteById(id);
        return "redirect:list";
    }
    


}
