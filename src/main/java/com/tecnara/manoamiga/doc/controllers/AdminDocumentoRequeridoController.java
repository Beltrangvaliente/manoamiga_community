package com.tecnara.manoamiga.doc.controllers;

import antlr.Utils;
import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.entities.CursoEntity;
import com.tecnara.manoamiga.doc.entities.DocumentoRequeridoEntity;
import com.tecnara.manoamiga.doc.repositories.IDocumentoRequeridoRepository;
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
@RequestMapping("/doc/admin/documento_requerido")

public class AdminDocumentoRequeridoController extends TecnaraBaseController {

    @Autowired
    private IDocumentoRequeridoRepository repoRequerido;

    @Autowired
    private IDocumentoRequeridoRepository repoDocumento;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new DocumentoRequeridoEntity());
        } else {
            m.addAttribute("registro", repoRequerido.findById(id).get());
        }
        return "doc/admin/documento_requerido_form";
    }

    @GetMapping("/formsinmenu")
    public String formsinmenu(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new DocumentoRequeridoEntity());
        } else {
            m.addAttribute("registro", repoRequerido.findById(id).get());
        }
        m.addAttribute("sinmenu", true);
        return "doc/admin/documento_requerido_form";
    }

    @GetMapping("/list")
    public String list(Model m) {
        return "doc/admin/documento_requerido_list";
    }

    @GetMapping("/help")
    public String help(Model m) {
        return "doc/admin/documento_requerido_help";
    }

    @PostMapping("/guardar")
    public String guardar(DocumentoRequeridoEntity registro) {
        repoRequerido.save(registro);
        return "redirect:list";

    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<DocumentoRequeridoEntity> list() {
        return repoRequerido.findAll();

    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoRequerido.deleteById(id);
        return "redirect:list";

    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoDocumento.findByTipoDeDocumentoContainingIgnoreCaseOrDescripcionContainingIgnoreCase(filtro, filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getTipoDeDocumento() + " " + (x.getDescripcion() == null ? "" : x.getDescripcion())))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        DocumentoRequeridoEntity x = repoDocumento.findById(id).orElse(new DocumentoRequeridoEntity());
        return x.getTipoDeDocumento() + " " + (x.getDescripcion() == null ? "" : x.getDescripcion());
    }

    /*
    @GetMapping("/buscador_alumno")
    @ResponseBody
    public List<ClaveValorDto> buscador(Long id_alumno, String filtro) {
        return repoDocumento.findByTipoDeDocumentoContainingIgnoreCaseOrDescripcionContainingIgnoreCase(filtro, filtro)
                .stream()
                .map(x -> new ClaveValorDto(Long.parseLong(x.get("id")), x.get("tipo_de_documento")))
                .collect(Collectors.toList());
    }*/
}
