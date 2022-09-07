package com.tecnara.manoamiga.doc.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.dto.ProfesorDto;
import com.tecnara.manoamiga.doc.entities.AlumnoEntity;
import com.tecnara.manoamiga.doc.entities.ProfesorEntity;
import com.tecnara.manoamiga.doc.repositories.IProfesorRepository;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody; 

@Controller("doc_admin_profesor_controller")
@RequestMapping("/doc/admin/profesor")
public class AdminProfesorController extends TecnaraBaseController{

    @Autowired
    private IProfesorRepository repoProfesor;

    @Autowired
    private IUtilidadesService utils;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new ProfesorEntity());
        } else {
            m.addAttribute("registro", repoProfesor.findById(id).get());
        }
        return "doc/admin/profesor_form";
    }

    @GetMapping("/formsinmenu")
    public String formsinmenu(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new ProfesorEntity());
        } else {
            m.addAttribute("registro", repoProfesor.findById(id).get());
        }

        m.addAttribute("sinmenu", true);
        return "doc/admin/profesor_form";
    }

    @GetMapping("/list")
    public String list(Model m) {
        return "doc/admin/profesor_list";
    }

    @GetMapping("/help")
    public String help(Model m) {
        return "doc/admin/profesor_help";
    }

    @PostMapping("/guardar")
    public String guardar(ProfesorEntity profesor) {
        utils.guardarObjetoValoresNoNulos(repoProfesor, profesor);
        return "redirect:list";
    }
    
    @Autowired
    private IUsuarioRepository repoUsuario;

    public ProfesorDto convertir(ProfesorEntity origen) {
        ProfesorDto nuevo = new ProfesorDto();
        nuevo.setId(origen.getId());
        nuevo.setNombreProf(origen.getNombreProf());
        nuevo.setApellido1Prof(origen.getApellido1Prof());
        nuevo.setApellido2Prof(origen.getApellido2Prof());
        
        UsuarioEntity origen2=repoUsuario.findById(origen.getIdUsuario()).orElse(new UsuarioEntity());      
        nuevo.setUsuario(origen2.getUsuario());
        return nuevo;
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData(String filtro) {
              return repoProfesor.adminList(filtro);
    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoProfesor.findByNombreProfContainingIgnoreCaseOrApellido1ProfContainingIgnoreCaseOrApellido2ProfContainingIgnoreCase(filtro, filtro, filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getNombreProf() + " " + x.getApellido1Prof() + " " + x.getApellido2Prof()))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        ProfesorEntity x= utils.findById(repoProfesor, id, ProfesorEntity.class);
        return  x.getNombreProf() + " " + x.getApellido1Prof() + " " + x.getApellido2Prof();
    }      

    @GetMapping("/borrar")
    @Transactional
    public String borrar(Long id) {
        ProfesorEntity ent=repoProfesor.getById(id);
        repoProfesor.deleteById(id);
        if (ent.getIdUsuario()!=null && repoUsuario.existsById(ent.getIdUsuario())){
            repoUsuario.deleteById(ent.getIdUsuario());
        }                 
        
        return "redirect:list";
    }

}
