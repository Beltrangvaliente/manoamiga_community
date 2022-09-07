/*

 */
package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.entities.ActividadEntity;
import com.tecnara.manoamiga.act.entities.HorarioEntity;
import com.tecnara.manoamiga.act.repositories.IHorarioRepository;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/monitor/horario")

public class MonitorHorarioController extends TecnaraBaseController {

    @GetMapping("/list")
    public String list(Model m) {
        return "act/monitor/horario_list";
    }

    @Autowired
    private IHorarioRepository repoHorario;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new HorarioEntity());
        } else {
            m.addAttribute("registro", repoHorario.findById(id).get());
        }
        return "act/monitor/horario_form";
    }

    @PostMapping("/guardar")
    public String guardar(HorarioEntity registro) {
        repoHorario.save(registro);
        return "redirect:form";

    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoHorario.deleteById(id);
        return "redirect:form";
    }
    
    @Autowired
    private ModelMapper model;
    
    @GetMapping("/clonar")
    @ResponseBody
    public String duplicar(Long idHorario) {
        HorarioEntity hor=repoHorario.findById(idHorario).get();

        HorarioEntity nuevo=new HorarioEntity();
        if ("Periodica".equalsIgnoreCase(hor.getTipoActividad())){
            nuevo.setDiaSemana(hor.getDiaSemana()+1);
            if (nuevo.getDiaSemana()==7){
                nuevo.setDiaSemana(0);
            }
            nuevo.setFechaActividad(general.getFechaActual());
        }else{
            nuevo.setFechaActividad(general.addDays(hor.getFechaActividad(),1));
            nuevo.setDiaSemana(0);
        }
        nuevo.setFechaInicio(hor.getFechaInicio());
        nuevo.setFechaFin(hor.getFechaFin());
        nuevo.setHorarioFin(hor.getHorarioFin());
        nuevo.setHorarioInicio(hor.getHorarioInicio());
        nuevo.setTipoActividad(hor.getTipoActividad());
        nuevo.setIdActividad(hor.getIdActividad());
        repoHorario.save(nuevo);
        return "ok";
    }
    

}
