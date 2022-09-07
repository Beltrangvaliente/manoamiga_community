package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.entities.ActividadEntity;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.EvaluacionBeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.EvaluacionMonitorEntity;
import com.tecnara.manoamiga.act.entities.HorarioEntity;
import com.tecnara.manoamiga.act.entities.SolicitudEvaluacionMonitorEntity;
import com.tecnara.manoamiga.act.repositories.IActividadRepository;
import com.tecnara.manoamiga.act.repositories.IBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IEvaluacionMonitorRepository;
import com.tecnara.manoamiga.act.repositories.ISolicitudEvaluacionMonitorRepository;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.entities.NotificacionEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/beneficiario/actividad")

public class BeneficiarioActividadController extends TecnaraBaseController {

    @Autowired
    private IBeneficiarioRepository repoBeneficiario;
    
    @Autowired
    private IActividadRepository repoAct;
    
    @Autowired
    private IEvaluacionMonitorRepository repoEvaluacion;
    
    @Autowired
    private IGeneral general;

    @GetMapping("/list")
    public String list(Model m) {
        m.addAttribute("hoy", repoAct.beneficiarioListHoy( general.getIdValidado() ) );
        m.addAttribute("evaluacion", repoAct.beneficiarioListEvaluacion(general.getIdValidado()) );
        m.addAttribute("semanal", repoAct.beneficiarioListSemanal(general.getIdValidado()));
        m.addAttribute("actividad", repoAct.findAll());
        /*m.addAttribute("monitor", repoAct.beneficiarioListMonitores(general.getIdValidado()));*/
        return "act/beneficiario/actividad_list"; 
    }

    @GetMapping("/votar")
    @ResponseBody
    public String votar(Long idActividad, Integer nota) {
        return "ok";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoAct.deleteById(id);
        return "redirect:form";
    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoBeneficiario.findByNombreContainingIgnoreCase(filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getNombre()))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        BeneficiarioEntity x = repoBeneficiario.findById(id).orElse(new BeneficiarioEntity());
        return x.getNombre();

    }
    @GetMapping("/list_data")
    @ResponseBody
    public List<ActividadEntity > listData() {
        return repoAct .findAll();
    }
   
    @GetMapping("/list_data_evaluacion_monitores")
    @ResponseBody
    public List<Map> listDataEvalMon() {
        return repoAct.beneficiarioListMonitores(general.getIdValidado());
    }
    
    @Autowired
    private ISolicitudEvaluacionMonitorRepository repoSolEvaMon;
    
    @GetMapping("/evaluar")
    @ResponseBody
    public String insertar(Long idMonitor, Long idSolicitudEvaluacionMonitor, Integer valoracion) {
        SolicitudEvaluacionMonitorEntity sem= repoSolEvaMon.findById(idSolicitudEvaluacionMonitor).get();
        Optional<EvaluacionMonitorEntity> x = repoEvaluacion.findByIdSolicitudEvaluacionMonitorAndIdMonitorAndIdBeneficiario(idSolicitudEvaluacionMonitor,idMonitor, general.getIdValidado());
        EvaluacionMonitorEntity eval;
        if (x.isPresent()){
            eval=x.get();
            eval.setValoracion(valoracion);
            eval.setIdActividad(sem.getIdActividad());
            eval.setFechaComentario(general.getFechaActual());
        }else{
            eval=new EvaluacionMonitorEntity();
            eval.setIdBeneficiario(general.getIdValidado());
            eval.setIdMonitor(idMonitor);
            eval.setIdActividad(sem.getIdActividad());
            eval.setIdSolicitudEvaluacionMonitor(idSolicitudEvaluacionMonitor);
            eval.setValoracion(valoracion);
            eval.setFechaComentario(general.getFechaActual());
        }
        repoEvaluacion.save(eval);
        return "ok";
    }
    
}
