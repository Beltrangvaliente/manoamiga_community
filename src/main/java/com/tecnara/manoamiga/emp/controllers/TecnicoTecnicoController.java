package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;

import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import com.tecnara.manoamiga.emp.entities.SectorEntity;

import com.tecnara.manoamiga.emp.entities.TecnicoEntity;
import com.tecnara.manoamiga.emp.repositories.ITecnicoRepository;
import com.tecnara.manoamiga.expo.entities.CoordinadorEntity;
import java.util.Date;

import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yaml.snakeyaml.events.Event;

@Controller
@RequestMapping("/emp/tecnico/tecnico")
public class TecnicoTecnicoController extends TecnaraBaseController {

    @Autowired
    private IUtilidadesService utils;

    @Autowired
    private ITecnicoRepository repoTecnico;

    @Autowired
    private IUsuarioRepository repoUsuario;

    @Autowired
    private IGeneral repoGeneral;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        //if no es super redirect
        if (repoGeneral.superAdministrador()==false){
            return "redirect:../index";
        }
        
        
        if (id == null) {
            m.addAttribute("registro", new TecnicoEntity());
        } else {
            m.addAttribute("registro", repoTecnico.findById(id).get());
        }
        return "emp/tecnico/tecnico_form";

    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoTecnico.findByNombreContainingOrApellidosContaining(filtro, filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getNombre() + " " + x.getApellidos()))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        TecnicoEntity x = repoTecnico.findById(id).orElse(new TecnicoEntity());
        return general.concat(x.getNombre(), x.getApellidos());
    }

    @GetMapping("/formsinmenu")
    public String forsinmenu(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new TecnicoEntity());
        } else {
            m.addAttribute("registro", repoTecnico.findById(id).get());
        }
        m.addAttribute("sinmenu", true);
        return "emp/tecnico/tecnico_form";
    }

    @GetMapping("list")
    public String list(Model m) {
        //if no es super redirect
        if (repoGeneral.superAdministrador()==false){
            return "redirect:../index";
        }

        return "emp/tecnico/tecnico_list";
    }

    //@GetMapping("/list_data")
    //@ResponseBody
    //public List<TecnicoEntity> listData() {
    //return repoTecnico.findAll();
    //}
    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData(String filtro) {
        //if no es super redirect
        if (repoGeneral.superAdministrador()==false){
            return null;
        }


        return repoTecnico.tecnicoList(filtro);
    }

    @GetMapping("/help")
    public String help(Model m) {
        return "emp/tecnico/participante_help";
    }

    @PostMapping("/guardar")
    public String guardar(TecnicoEntity registro) {
        repoTecnico.save(registro);
        return "redirect:list";
    }

    @GetMapping("/borrar")
    @Transactional
    public String borrar(Long id) {
        TecnicoEntity ent = repoTecnico.getById(id);
        repoTecnico.deleteById(id);
        if (ent.getIdUsuario() != null && repoUsuario.existsById(ent.getIdUsuario())) {
            repoUsuario.deleteById(ent.getIdUsuario());
        }
        return "redirect:list";
    }

    @GetMapping("/buscador_tecnico_id")
    @ResponseBody
    public String buscadorTrabajador(Long id) {
        UsuarioEntity x = repoUsuario.findById(id).orElse(new UsuarioEntity());
        return x.getUsuario();
    }

    @GetMapping("/buscador_tecnico")
    @ResponseBody
    public List<ClaveValorDto> buscadorTrabajador(String filtro, Long idUsuario) {
        return repoTecnico.tecnicoFromBuscador(filtro, idUsuario)
                .stream()
                .map(x -> new ClaveValorDto(Long.parseLong(x.get("id") + ""), x.get("usuario") + ""))
                .collect(Collectors.toList());

    }

  
}
