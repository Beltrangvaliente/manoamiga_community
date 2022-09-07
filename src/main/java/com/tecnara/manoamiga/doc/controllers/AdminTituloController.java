package com.tecnara.manoamiga.doc.controllers;


import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.dto.TituloDto;
import com.tecnara.manoamiga.doc.entities.AlumnoEntity;
import com.tecnara.manoamiga.doc.entities.MatriculaEntity;
import com.tecnara.manoamiga.doc.entities.TituloEntity;
import com.tecnara.manoamiga.doc.repositories.IAlumnoRepository;
import com.tecnara.manoamiga.doc.repositories.ITituloRepository;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
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

@Controller
@RequestMapping("/doc/admin/titulo")
public class AdminTituloController extends TecnaraBaseController{

    @Autowired
    private IUtilidadesService utils;
    
    @Autowired
    private ITituloRepository repoTitulo;
    
    @Autowired
    private IAlumnoRepository repoAlumno;

    
    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new TituloEntity());
        } else {
            m.addAttribute("registro", repoTitulo.findById(id).get());
        }
        return "doc/admin/titulo_form"; //Esto es el html
    }

    @GetMapping("/formsinmenu")
    public String formsinmenu(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new TituloEntity());
        } else {
            m.addAttribute("registro", repoTitulo.findById(id).get());
        }
        m.addAttribute("sinmenu", true);
        return "doc/admin/titulo_form"; //Esto es el html
    }

    @GetMapping("/list")
    public String list(Model m) {
        return "doc/admin/titulo_list";
    }

    @GetMapping("/help")
    public String help(Model m) {
        return "doc/admin/titulo_help";
    }

    @PostMapping("/guardar")
    public String guardar(TituloEntity registro) {
        repoTitulo.save(registro);
        return "redirect:list";

    }
    
    public TituloDto convertirDto (TituloEntity origen){
        TituloDto nuevo=new TituloDto();
        nuevo.setId(origen.getId()); 
        nuevo.setNivel(origen.getNivel());
        nuevo.setTitulacion(origen.getTitulacion());
        
        AlumnoEntity alumno=utils.findById(repoAlumno, origen.getIdAlumno(), AlumnoEntity.class);
        nuevo.setNombreAlumno(alumno.getNombre() + " " + alumno.getApellido1() + " " + alumno.getApellido2());
        
        return nuevo;
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData(String filtro) {
        return repoTitulo.adminTituloList(filtro);
    }
    
    @GetMapping("/borrar")
    public String borrar (Long id){
        repoTitulo.deleteById(id); 
        return "redirect:list";
    }


}

