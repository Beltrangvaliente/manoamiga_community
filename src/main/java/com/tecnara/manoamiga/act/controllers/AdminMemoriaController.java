package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.entities.MemoriaEntity;
import com.tecnara.manoamiga.act.repositories.IMemoriaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/admin/memoria")
public class AdminMemoriaController extends TecnaraBaseController{

    @GetMapping("/form")
    public String form(Model m) {

        m.addAttribute("registro", new MemoriaEntity());
        return "act/admin/memoria_form";
    }

    @GetMapping("/list")
    public String list(Model m) {
        return "act/admin/memoria_list";
    }
    @Autowired
    private IMemoriaRepository repoMemoria;

    @GetMapping("/list_data")
    @ResponseBody
    public List<MemoriaEntity> listData() {
        return repoMemoria.findAll();
    }

    @PostMapping("/guardar")
    public String guardar(MemoriaEntity registro) {
        repoMemoria.save(registro);
        return "redirect:list";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {

        repoMemoria.deleteById(id);

        return "redirect:list";
    }

}
