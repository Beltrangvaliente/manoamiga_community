package com.tecnara.manoamiga.doc.controllers;


import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.entities.CentroAtencionEntity;
import com.tecnara.manoamiga.doc.repositories.ICentroAtencionRepository;
import com.tecnara.manoamiga.doc.services.ICitaService;
import com.tecnara.manoamiga.doc.services.IUtilidadesService;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/doc/admin/centro_atencion")
public class AdminCentroAtencionController extends TecnaraBaseController{

    @Autowired
    private IUtilidadesService utils;
    
    @Autowired
    private ICentroAtencionRepository repoCentroAtencion;
    
    @Autowired
    private ICitaService repoCitas;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new CentroAtencionEntity());

        } else {
            m.addAttribute("registro", repoCentroAtencion.findById(id).get());
        }
        return "doc/admin/centro_atencion_form";//Esto es el html

    }

    @GetMapping("/formsinmenu")
    public String formsinmenu(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new CentroAtencionEntity());
        } else {
            m.addAttribute("registro", repoCentroAtencion.findById(id).get());
        }

        m.addAttribute("sinmenu", true);

        return "doc/admin/centro_atencion_form";//Esto es el html

    }

    @GetMapping("/list")
    public String list(Model m) {
        return "doc/admin/centro_atencion_list";

    }

    @GetMapping("/help")
    public String help(Model m) {
        return "doc/admin/centro_atencion_help";
    }

    @PostMapping("/guardar")
    public String guardar(CentroAtencionEntity registro) {
        repoCentroAtencion.save(registro);
        return "redirect:list";
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<CentroAtencionEntity> listData() {
        return repoCentroAtencion.findAll();

    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoCentroAtencion.findByNombreContainingIgnoreCase(filtro)
                .stream().map(x -> new ClaveValorDto(x.getId(), x.getNombre())).collect(Collectors.toList());

    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        CentroAtencionEntity x= utils.findById(repoCentroAtencion, id, CentroAtencionEntity.class);
        return x.getNombre();
    }    
    

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoCentroAtencion.deleteById(id);
        return "redirect:list";

    }
    
    

            
}
