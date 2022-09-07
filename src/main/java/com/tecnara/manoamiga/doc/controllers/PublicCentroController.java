package com.tecnara.manoamiga.doc.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.entities.CentroEntity;
import com.tecnara.manoamiga.doc.entities.CursoEntity;
import com.tecnara.manoamiga.doc.entities.ProfesorEntity;
import com.tecnara.manoamiga.doc.repositories.ICentroRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/doc/public/centro")
public class PublicCentroController extends TecnaraBaseController {

    @Autowired
    private IGeneral general;
    
    @Autowired
    private ICentroRepository repoCentro;

    @GetMapping("/show")
    public String show(Model m, Long id) {
        m.addAttribute("registro", repoCentro.findById(id).get());
        return "doc/public/centro_show";
    }
    
    @Autowired
    private ModelMapper mapper;

    
    @GetMapping("/prueba")
    @ResponseBody
    public List<CentroEntity> l(){
        List<CentroEntity> lista=repoCentro.publicCentroList("")
                                             .stream()
                                             .map(x->mapper.map(x, CentroEntity.class))
                                             .collect(Collectors.toList());
        return lista;
    }
    
    @GetMapping("/list")
    public String list(Model m, String filtro) {
        if (filtro==null){
            filtro="";
        }
        List<CentroEntity> lista=repoCentro.publicCentroList(filtro)
                                             .stream()
                                             .map(x->mapper.map(x, CentroEntity.class))
                                             .collect(Collectors.toList());
        m.addAttribute("lista", lista);
        m.addAttribute("filtro", filtro);
        return "doc/public/centro_list";
    }

   
}
