
package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.act.entities.AdministradorEntity;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.ProfesorEntity;
import com.tecnara.manoamiga.act.repositories.IAdministradorRepository;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
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

@Controller
@RequestMapping ("/act/admin/administrador")
public class AdminAdministradorController extends TecnaraBaseController{
    
    @Autowired
    private IAdministradorRepository repoAdministrador;
    
    @GetMapping("/list")
    public String list(Model m){
        return "act/admin/administrador_list";    
    }    
    
    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id==null){
            m.addAttribute("registro", new AdministradorEntity());
        }else{
            m.addAttribute("registro", repoAdministrador.findById(id).get());
        }
        return "act/admin/administrador_form";
    }  
     @PostMapping("/guardar")
    public String guardar(AdministradorEntity registro) {
        repoAdministrador.save(registro);
        return "redirect:list";
    }
    
    @PostMapping("/guardar_ajax")
    @ResponseBody
    public Long guardarAjax(AdministradorEntity registro) {
        repoAdministrador.save(registro);
        return registro.getId();
    }    
    
    @GetMapping("/borrar")
    @Transactional
    public String borrar(Long id) {
        AdministradorEntity ent=repoAdministrador.getById(id);
        repoAdministrador.deleteById(id);
        if (ent.getIdUsuario()!=null && repoUsuario.existsById(ent.getIdUsuario())){
            repoUsuario.deleteById(ent.getIdUsuario());
        }        
        return "redirect:list";
    } 
    
    @GetMapping("/list_data")
    @ResponseBody // para que devuelva un JSON
    public List<Map> listData() {
        return repoAdministrador.adminList(); // nos devuelve todos los datos de la tabla; sin filtrar
    }

    @Autowired
    private IUsuarioRepository repoUsuario;

    @GetMapping("/buscador_admin")
    @ResponseBody
    public List<ClaveValorDto> buscador (String filtro, Long idUsuario){
        return repoAdministrador.adminAdministradorFromBuscador(filtro,idUsuario).stream()
                .map(x->new ClaveValorDto(Long.parseLong(x.get("id")+""), x.get("usuario")+""))
                .collect(Collectors.toList());
    }    
    
    @GetMapping("/buscador_admin_id")
    @ResponseBody
    public String buscador(Long id){
        UsuarioEntity x= repoUsuario.findById(id).orElse(new UsuarioEntity());
        return x.getUsuario();
    }         
    
}
