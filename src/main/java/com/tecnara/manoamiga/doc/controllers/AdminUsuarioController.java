package com.tecnara.manoamiga.doc.controllers;


import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
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

@Controller("doc_admin_usuario_controller")
@RequestMapping("/doc/admin/usuario")
public class AdminUsuarioController extends TecnaraBaseController{

    @Autowired
    private IUtilidadesService utils;
    
    @Autowired
    private IUsuarioRepository repoUser;
    
    @GetMapping("/form")
    public String form(Model m) {

        m.addAttribute("registro", new UsuarioEntity());
        return "doc/admin/usuario_form";
    }

    @GetMapping("/list")
    public String list(Model m){
        return "doc/admin/usuario_list";    
    }
    
    @GetMapping("/help")
    public String help(Model m){
        return "doc/admin/usuario_help";
    }
    @PostMapping ("/guardar")
    public String guardar(UsuarioEntity registro){
    repoUser.save(registro);
    return "redirect:list";
    }
    @GetMapping ("/list_data")
    @ResponseBody
    public List<UsuarioEntity> listData(){
    return repoUser.findAll();
    }
    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro){
        return repoUser.findByUsuarioContainingIgnoreCaseOrEmailContainingIgnoreCase(filtro, filtro)
                .stream()
                .map(x-> new ClaveValorDto(x.getId(), x.getUsuario()+ " (" + x.getEmail() + ")"))
                .collect(Collectors.toList()); 
    }    
    
    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id){
        UsuarioEntity x=utils.findById(repoUser, id, UsuarioEntity.class);
        return x.getUsuario()+ " (" + x.getEmail() + ")";
    }        
    
}
