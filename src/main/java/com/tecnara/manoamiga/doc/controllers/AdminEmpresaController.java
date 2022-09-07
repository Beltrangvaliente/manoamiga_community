package com.tecnara.manoamiga.doc.controllers;


import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.entities.EmpresaEntity;
import com.tecnara.manoamiga.doc.repositories.IEmpresaRepository;
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
@RequestMapping("/doc/admin/empresa")
public class AdminEmpresaController extends TecnaraBaseController{

    @Autowired
    private IUtilidadesService utils;
    
    @Autowired
    private IEmpresaRepository repoEmpresa;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new EmpresaEntity());
        } else {
            m.addAttribute("registro", repoEmpresa.findById(id).get());
        }
        return "doc/admin/empresa_form";
    }

    @GetMapping("/formsinmenu")
    public String formsinmenu(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new EmpresaEntity());
        } else {
            m.addAttribute("registro", repoEmpresa.findById(id).get());
        }
        m.addAttribute("sinmenu", true);
        return "doc/admin/empresa_form";
    }

    @PostMapping("/guardar")
    public String guardar(EmpresaEntity registro) {
        repoEmpresa.save(registro);
        return "redirect:list";
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<EmpresaEntity> listData() {
        return repoEmpresa.findAll();
    }

    @GetMapping("/list")
    public String list(Model m) {
        return "doc/admin/empresa_list";

    }

    @GetMapping("/help")
    public String help(Model m) {
        return "doc/admin/empresa_help";

    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoEmpresa.findByNombreContainingIgnoreCase(filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getNombre()))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        EmpresaEntity x= utils.findById(repoEmpresa, id, EmpresaEntity.class);
        return x.getNombre();
    }       
    
    @GetMapping ("/borrar")
    public String borrar(Long id) {
        repoEmpresa.deleteById(id);
        return "redirect:list";
    }
}
