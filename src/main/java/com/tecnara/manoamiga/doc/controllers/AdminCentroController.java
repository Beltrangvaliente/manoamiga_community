package com.tecnara.manoamiga.doc.controllers;


import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.entities.CentroEntity;
import com.tecnara.manoamiga.doc.repositories.ICentroRepository;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/doc/admin/centro")
public class AdminCentroController extends TecnaraBaseController{

    @Autowired
    private ICentroRepository repoCentro;   //dao - Data Access Object
    
    @Autowired
    private IUtilidadesService utils;
    
    @GetMapping("/form")
    public String form(Model m, Long id) {
        if(id==null){
        m.addAttribute("registro", new CentroEntity());
        }else{
            m.addAttribute("registro", repoCentro.findById(id).get()); 
        }
        return "doc/admin/centro_form"; //html
    }
    
    @GetMapping("/formsinmenu")
    public String formsinmenu(Model m, Long id) {
        if(id==null){
        m.addAttribute("registro", new CentroEntity());
        }else{
            m.addAttribute("registro", repoCentro.findById(id).get()); 
        }
        m.addAttribute("sinmenu", true);
        return "doc/admin/centro_form"; //html
    }

    @GetMapping("/list")
    public String list(Model m) {
        return "doc/admin/centro_list"; //html
    }
    
    @GetMapping("/help")
    public String help(Model m){
        return "doc/admin/centro_help";
    }

    @PostMapping("/guardar")
    public String guardar(CentroEntity registro){
        repoCentro.save(registro);
        return "redirect:list";
    }
    
    @GetMapping("/borrar")
    public String borrar(Long id){
        repoCentro.deleteById(id);//repositorio para acceder a los datos 
        return "redirect:list"; //para que vuelva al listado otra vez con lo ya borrado
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<CentroEntity> listData(){
        return repoCentro.findAll();
    }
        
    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro){
        return repoCentro.findByNombreContainingIgnoreCase(filtro)
                         .stream()
                         .map(x-> new ClaveValorDto(x.getId(), x.getNombre()))
                         .collect(Collectors.toList());
    }
    
    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        CentroEntity x= utils.findById(repoCentro, id, CentroEntity.class);
        return x.getNombre();
    }        
    
            
}
