package com.tecnara.manoamiga.doc.controllers;

import antlr.Utils;
import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.entities.NecesidadFormacionEntity;
import com.tecnara.manoamiga.doc.repositories.INecesidadFormacionRepository;
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
@RequestMapping("/doc/admin/necesidad_formacion")
public class AdminNecesidadFormacionController extends TecnaraBaseController {

    @Autowired
    private INecesidadFormacionRepository repoNecesidadFormacion;   //como nombre en algunas empresas puede ser con "dao"-data access object

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new NecesidadFormacionEntity());
        } else {
            m.addAttribute("registro", repoNecesidadFormacion.findById(id).get());
        }
        return "doc/admin/necesidad_formacion_form";
    }

    @GetMapping("/formsinmenu")
    public String formsinmenu(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new NecesidadFormacionEntity());
        } else {
            m.addAttribute("registro", repoNecesidadFormacion.findById(id).get());
        }
        m.addAttribute("sinmenu", true);
        return "doc/admin/necesidad_formacion/form"; 
    
    }
    
    @GetMapping("/list")
    public String list(Model m) {
        return "doc/admin/necesidad_formacion_list";
    }

    @GetMapping("/help")
    public String help(Model m) {
        return "doc/admin/necesidad_formacion_help";
    }

    @PostMapping("/guardar")
    public String guardar(NecesidadFormacionEntity registro) {
        repoNecesidadFormacion.save(registro); //los datos que vamos a guardar y se le pone ese nombre dado 
        return "redirect:list";
    }

    @GetMapping("/list_data") //porque no recibe muchos parámetros
    @ResponseBody //devuelve json
    public List<NecesidadFormacionEntity> listData(String filtro) { //al poner List delante significa que te puede devolver varios centros
        return repoNecesidadFormacion.findByNombreContainingOrIdiomaContainingOrEdadContaining(filtro,filtro,filtro); //findAll devuelve todos los datos de la tabla
    }

    
    
    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoNecesidadFormacion.deleteById(id);
        return "redirect:list";
    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoNecesidadFormacion.findByNombreContaining(filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getNombre()))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        NecesidadFormacionEntity x = repoNecesidadFormacion.findById(id).get();
        return x.getNombre();
    }
}
