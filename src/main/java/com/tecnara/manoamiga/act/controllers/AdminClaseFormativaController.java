 
package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.entities.ClaseFormativaEntity;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.act.repositories.IClaseFormativaRepository;
import com.tecnara.manoamiga.expo.entities.AreaEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/admin/clase_formativa")
public class AdminClaseFormativaController extends TecnaraBaseController{

    @Autowired
    private IClaseFormativaRepository repoClaseFormativa;

    @GetMapping("/list")
    public String list(Model m) {
        return "act/admin/clase_formativa_list";
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<ClaseFormativaEntity> listData() {
        return repoClaseFormativa.findAll();
    }

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id==null){
            m.addAttribute("registro", new ClaseFormativaEntity());    
        }else{
            m.addAttribute("registro", repoClaseFormativa.findById(id).get());    
        }
        
        return "act/admin/clase_formativa_form";
    }

    @PostMapping("/guardar")
    public String guardar(ClaseFormativaEntity registro) {
        repoClaseFormativa.save(registro);
        return "redirect:list";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoClaseFormativa.deleteById(id);
        return "redirect:list";
    }
}
