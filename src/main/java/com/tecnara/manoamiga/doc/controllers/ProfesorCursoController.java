package com.tecnara.manoamiga.doc.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.dto.CursoDto;
import com.tecnara.manoamiga.doc.entities.CursoEntity;
import com.tecnara.manoamiga.doc.repositories.IAsistenciaRepository;
import com.tecnara.manoamiga.doc.repositories.ICursoRepository;
import com.tecnara.manoamiga.doc.repositories.IParticipacionProfesorCursoRepository;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/doc/profesor/curso")
public class ProfesorCursoController extends TecnaraBaseController {
      
    @Autowired
    private IGeneral general;
    
    @Autowired
    private IParticipacionProfesorCursoRepository repoParticipacion;
    
    @Autowired
    private ICursoRepository repoCurso;
    
    
    @Autowired
    private ModelMapper mapper;
    

    @GetMapping("/list")
    public String list() {
        return "doc/profesor/curso_list";
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData(String filtro) {
        return repoCurso.profesorList(filtro, general.getIdValidado());
    }
    
    
    
    
    
    
    
    @GetMapping("/form")
    public String form() {
        return "doc/profesor/curso_form"; //HTML que queremos mostrar
    }

    @GetMapping("/show")
    public String show(Model m, Long id) {
        //m.addAttribute("prof", mapper.map(repoParticipacion.profesorShow(id, general.getIdValidado()).get(), CursoDto.class ) );
        m.addAttribute("prof", repoParticipacion.profesorShow(id, general.getIdValidado()).get());
        //m.addAttribute("prof", repoParticipacion.profesorShow(id, general.getIdValidado()) ); 
        
        return "doc/profesor/curso_show"; //HTML que queremos mostrar
    }

  
    
    @Autowired
    public IAsistenciaRepository repoAsistencia;

 

    @GetMapping("/buscador")
    @ResponseBody
    public List<Map> buscador(String filtro) {
        return repoCurso.buscador(filtro, general.getIdValidado());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        CursoEntity x = repoCurso.findById(id).orElse(new CursoEntity());
        return x.getNombreCurso();
    }

    /*
    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData(String filtro, Long idCurso) {
        
        return repoAsistencia.profesorPasarLista( filtro, idCurso,);

    }*/
}