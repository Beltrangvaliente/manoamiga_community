


package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.entities.ComentarioEntity;

import com.tecnara.manoamiga.act.repositories.IComentarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/admin/comentario")
public class AdminComentarioController extends TecnaraBaseController {

    @Autowired
    private IComentarioRepository repoComentario;

    @GetMapping("/list")
    public String list(Model m) {
        return "act/admin/comentario_list";
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<ComentarioEntity> listData() {
        return repoComentario.findAll();
    }

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id==null){
            m.addAttribute("registro", new ComentarioEntity());
        }else{
            m.addAttribute("registro", repoComentario.findById(id).get());
        }
        
        return "act/admin/comentario_form";
    }

    @PostMapping("/guardar")
    public String guardar(ComentarioEntity registro) {
        repoComentario.save(registro);
        return "redirect:list";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoComentario.deleteById(id);
        return "redirect:list";
    }

}
