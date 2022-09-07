
package com.tecnara.manoamiga.doc.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.entities.DocumentoSolicitadoEntity;
import com.tecnara.manoamiga.doc.repositories.IDocumentoRequeridoRepository;
import com.tecnara.manoamiga.doc.repositories.IDocumentoSolicitadoRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/doc/admin/documento_solicitado")
public class AdminDocumentoSolicitadoController extends TecnaraBaseController{
    
    
    @Autowired
    private IDocumentoSolicitadoRepository repoDocuSolicitado;
    
    @Autowired
    private IGeneral general;
    
        @Autowired
    private IDocumentoRequeridoRepository repoRequerido;
    
       @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new DocumentoSolicitadoEntity());
        } else {
            m.addAttribute("registro", repoDocuSolicitado.findById(id).get());
        }
        return "doc/admin/documento_solicitado_form";
    }

    @GetMapping("/formsinmenu")
    public String formsinmenu(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new DocumentoSolicitadoEntity());
        } else {
            m.addAttribute("registro", repoDocuSolicitado.findById(id).get());
        }
        m.addAttribute("sinmenu", true);
        return "doc/admin/documento_solicitado_form";
    }    
    
    @GetMapping("/list")
    public String list(Model m) {
        return "doc/admin/documento_solicitado_list";
    }

    @GetMapping("/help")
    public String help(Model m) {
        return "doc/admin/documento_solicitado_help";
    }

    @PostMapping("/guardar")
    public String guardar(DocumentoSolicitadoEntity registro) {
        repoDocuSolicitado.save(registro);
        return "redirect:form";

    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<DocumentoSolicitadoEntity> list() {
        return repoDocuSolicitado.findAll();

    }


    @GetMapping("/borrar")
    public String borrar(Long id){
        repoDocuSolicitado.deleteById(id);
        return "redirect:form";
        
    }
    
    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro, Long idAlumno) {
        return repoDocuSolicitado.alumnoBuscador(filtro, idAlumno)
                .stream()
                .map(x -> new ClaveValorDto(Long.parseLong(x.get("id")+""), x.get("descripcion")+""))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        Map x = repoDocuSolicitado.alumnoBuscadorId(id).orElse(new HashMap<>());
        return x.get("descripcion")+"";
    }
    
    
}
