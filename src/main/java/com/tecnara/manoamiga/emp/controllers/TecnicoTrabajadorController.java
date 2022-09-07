package com.tecnara.manoamiga.emp.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.dtos.EmailInfoDto;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.IEmailService;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import com.tecnara.manoamiga.emp.entities.TecnicoEntity;

import com.tecnara.manoamiga.emp.entities.TrabajadorEntity;
import com.tecnara.manoamiga.emp.repositories.ITecnicoRepository;
import com.tecnara.manoamiga.emp.repositories.ITrabajadorRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/emp/tecnico/trabajador")
public class TecnicoTrabajadorController extends TecnaraBaseController {

    @Autowired
    private ITrabajadorRepository repoTrabajador;

    @Autowired
    private IUtilidadesService utils;

    @Autowired
    private ITecnicoRepository repoTecnico;

    @Autowired
    private IUsuarioRepository repoUsuario;

    @Autowired
    private IGeneral repoGeneral;

    @Autowired
    private IEmailService mail;

    @GetMapping("list")
    public String list(Model m) {
        return "emp/tecnico/trabajador_list";
    }

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new TrabajadorEntity());
        } else {
            m.addAttribute("registro", repoTrabajador.findById(id).get());
        }
        return "emp/tecnico/trabajador_form";

    }

    @PostMapping("/guardar")
    public String guardar(TrabajadorEntity registro) {
        repoTrabajador.save(registro);
        return "redirect:list";
    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoTrabajador.findByNombreContainingOrApellido1ContainingOrApellido2Containing(filtro, filtro, filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getNombre() + " " + x.getApellido1() + " " + x.getApellido2()))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        TrabajadorEntity x = repoTrabajador.findById(id).orElse(new TrabajadorEntity());
        return general.concat(x.getNombre(), x.getApellido1(), x.getApellido2());
    }

    @GetMapping("/formsinmenu")
    public String forsinmenu(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new TrabajadorEntity());
        } else {
            m.addAttribute("registro", repoTrabajador.findById(id).get());
        }
        m.addAttribute("sinmenu", true);
        return "emp/tecnico/trabajador_form";
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData(String filtro) {
        return repoTrabajador.tecnicoList(filtro);
    }

    @GetMapping("/buscador_trabajador_id")
    @ResponseBody
    public String buscadorTrabajador(Long id) {
        UsuarioEntity x = repoUsuario.findById(id).orElse(new UsuarioEntity());
        return x.getUsuario();
    }

    @GetMapping("/buscador_trabajador")
    @ResponseBody
    public List<ClaveValorDto> buscadorTrabajador(String filtro) {
      List<UsuarioEntity>usuario=repoUsuario.tecnicoTrabajadorBuscador(filtro);
      List<ClaveValorDto> res=usuario.stream()
              .map(x->new ClaveValorDto(x.getId(),x.getUsuario()))
              .collect(Collectors.toList());
      return res;
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoTrabajador.deleteById(id);
        return "redirect:list";
    }

    
    
    
}
