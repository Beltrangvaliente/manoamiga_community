/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import com.tecnara.manoamiga.emp.entities.ContratoEntity;
import com.tecnara.manoamiga.emp.entities.EmpresaEntity;
import com.tecnara.manoamiga.emp.entities.ProyectoEntity;
import com.tecnara.manoamiga.emp.entities.SectorEntity;
import com.tecnara.manoamiga.emp.repositories.IEmpresaRepository;
import com.tecnara.manoamiga.emp.repositories.IProyectoRepository;
import java.util.Date;
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

/**
 *
 * @author tecnara
 */
@Controller("emp_tecnico_empresa_controller")
@RequestMapping("/emp/tecnico/empresa")

public class TecnicoEmpresaController extends TecnaraBaseController {

    @Autowired
    private IUtilidadesService utils;

    @Autowired
    private IEmpresaRepository repoEmpresa;

    @Autowired
    private IProyectoRepository repoProyecto;
    
    

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new EmpresaEntity());
        } else {
            m.addAttribute("registro", repoEmpresa.findById(id).get());
        }
        return "emp/tecnico/empresa_form";

    }

    @GetMapping("/formsinmenu")
    public String forsinmenu(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new EmpresaEntity());
        } else {
            m.addAttribute("registro", repoEmpresa.findById(id).get());
        }
        m.addAttribute("sinmenu", true);
        return "emp/tecnico/empresa_form";
    }

    @GetMapping("/list")
    public String list(Model m) {
        return "emp/tecnico/empresa_list";
    }
    
    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData(String filtro, Long idSector) {
        return repoEmpresa.empresaList(filtro, idSector);
        
    }

    @GetMapping("/help")
    public String help(Model m) {
        return "emp/tecnico/empresa_help";
    }

    @PostMapping("guardar")
    public String guardar(EmpresaEntity registro) {
        repoEmpresa.save(registro);
        return "redirect:list";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoEmpresa.deleteById(id);
        return "redirect:list";
    }

    @GetMapping("/list_data_proyectos")
    @ResponseBody
    public List<Map> listDataProyectos(Long idEmpresa) {
        return repoEmpresa.tecnicoFormProyectos(idEmpresa);

    }
    
    @GetMapping("/list_data_participantes")
    @ResponseBody
    public List<Map> listDataParticipantes (Long idEmpresa) {
        return repoEmpresa.tecnicoFormParticipantes(idEmpresa);
    }
    
    @GetMapping("/list_data_contactos_empresa")
    @ResponseBody
    public List<Map> listDataContactos(Long idEmpresa) {
        return repoEmpresa.tecnicoFormContactosEmpresa(idEmpresa);
    }
    
    
    
   @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoEmpresa.findByNombreContaining(filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getNombre()))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id){
        EmpresaEntity x= repoEmpresa.findById(id).get();
        return x.getNombre();
    }
    

}
