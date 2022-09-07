package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.dto.ComentarioMonitorDto;
import com.tecnara.manoamiga.act.dto.ComentarioParaBeneficiarioDto;
import com.tecnara.manoamiga.act.dto.EvaluacionDelBeneficiarioDto;
import com.tecnara.manoamiga.act.dto.EvaluacionDto;
import com.tecnara.manoamiga.act.dto.FaltaDto;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.ComentarioEntity;
import com.tecnara.manoamiga.act.entities.EvaluacionBeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.FaltaAsistenciaEntity;
import com.tecnara.manoamiga.act.entities.MonitorEntity;
import com.tecnara.manoamiga.act.entities.ProfesorEntity;
import com.tecnara.manoamiga.act.entities.TutorEntity;

import com.tecnara.manoamiga.act.repositories.IBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IComentarioRepository;
import com.tecnara.manoamiga.act.repositories.IEvaluacionBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IFaltaAsistenciaRepository;
import com.tecnara.manoamiga.act.repositories.IMonitorRepository;
import com.tecnara.manoamiga.act.repositories.IProfesorRepository;
import com.tecnara.manoamiga.act.repositories.ITutorRepository;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/tutor/beneficiario")

public class TutorBeneficiarioController extends TecnaraBaseController {

    @Autowired
    private IGeneral general;

    @Autowired
    private IBeneficiarioRepository repoBeneficiario;

    @Autowired
    private IComentarioRepository repoComentario;

    @Autowired
    private IEvaluacionBeneficiarioRepository repoEvaluacion;

    @Autowired
    private IFaltaAsistenciaRepository repoFalta;

    @Autowired
    private IMonitorRepository repoMonitor;

    @Autowired
    private ITutorRepository repoTutor;

    @Autowired
    private IProfesorRepository repoProfesor;

    @GetMapping("/list")

    public String list(Model m) {

        List lsBen=repoBeneficiario.notificacionSinLeer(general.getIdValidado());
        
        m.addAttribute("beneficiario", lsBen);
        return "act/tutor/beneficiario_list";
    }
    
    @GetMapping("/Id")
    @ResponseBody
    public Long id(Model m) {
        return general.getIdValidado();
    }    

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new BeneficiarioEntity());
        } else {
            m.addAttribute("registro", repoBeneficiario.findById(id).get());
        }

        return "act/tutor/beneficiario_form";
    }

    @PostMapping("/guardar")
    public String guardar(BeneficiarioEntity registro) {
        registro.setIdTutor(general.getIdValidado());
        repoBeneficiario.save(registro);
        return "redirect:list";
    }

    /*@GetMapping("/list_data")
    @ResponseBody
    public List<BeneficiarioEntity> listData() {
        return repoBeneficiario.findAll();

    }*/

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoBeneficiario.deleteById(id);
        return "redirect:list";
    }

    @GetMapping("/list_data_comentario")
    @ResponseBody
    public List<ComentarioParaBeneficiarioDto> listDataComentario(Long idBeneficiario) {
        return repoComentario.findByIdBeneficiarioOrderByFechaComentarioDescHoraComentarioDesc(idBeneficiario)
                .stream()
                .map(x -> convertDto(x))
                .collect(Collectors.toList());
    }

    @GetMapping("/marcar_leido_comentario")
    @ResponseBody
    public String marcarLeidoComentario(Long idBeneficiario) {
        repoBeneficiario.tutorFormComentarioMarcarLeido(idBeneficiario, general.getIdValidado());
        return "ok";
    }

    public ComentarioParaBeneficiarioDto convertDto(ComentarioEntity origen) {

        ComentarioParaBeneficiarioDto nuevo = mapper.map(origen, ComentarioParaBeneficiarioDto.class);

        if (origen.getIdMonitor() != null) {

            MonitorEntity monitor = repoMonitor.findById(origen.getIdMonitor()).orElse(new MonitorEntity());
            nuevo.setPersona(general.concat("Monitor - ", monitor.getNombre(), monitor.getApellido1(), monitor.getApellido2()));

        } else if (origen.getIdTutor() != null) {

            TutorEntity tutor = repoTutor.findById(origen.getIdTutor()).orElse(new TutorEntity());
            nuevo.setPersona(general.concat("Tutor - ", tutor.getNombre(), tutor.getApellido()));
        } else if (origen.getIdProfesor() != null) {

            ProfesorEntity profesor = repoProfesor.findById(origen.getIdProfesor()).orElse(new ProfesorEntity());
            nuevo.setPersona(general.concat("Profesor - ", profesor.getNombre(), profesor.getApellido1(), profesor.getApellido2()));
        }
        return nuevo;
    }

    //Convertir el entity al dto
    public EvaluacionDelBeneficiarioDto convertDto(EvaluacionBeneficiarioEntity origen) {
        EvaluacionDelBeneficiarioDto nuevo = mapper.map(origen, EvaluacionDelBeneficiarioDto.class);

        if (origen.getIdMonitor() != null) {

            MonitorEntity monitor = repoMonitor.findById(origen.getIdMonitor()).orElse(new MonitorEntity());

            nuevo.setPersona(general.concat("Monitor - ", monitor.getNombre(), monitor.getApellido1(), monitor.getApellido2()));

        } else if (origen.getIdProfesor() != null) {

            ProfesorEntity profesor = repoProfesor.findById(origen.getIdProfesor()).orElse(new ProfesorEntity());

            nuevo.setPersona(general.concat("Profesor - ", profesor.getNombre(), profesor.getApellido1(), profesor.getApellido2()));

        }
        return nuevo;
    }

    //falta de asistencia
    public FaltaDto convertDto(FaltaAsistenciaEntity origen) {
        FaltaDto nuevo = mapper.map(origen, FaltaDto.class);

        if (origen.getIdMonitor() != null) {

            MonitorEntity monitor = repoMonitor.findById(origen.getIdMonitor()).orElse(new MonitorEntity());
            nuevo.setPersona(general.concat("Monitor - ", monitor.getNombre(), monitor.getApellido1(), monitor.getApellido2()));

        } else if (origen.getIdProfesor() != null) {

            ProfesorEntity profesor = repoProfesor.findById(origen.getIdProfesor()).orElse(new ProfesorEntity());
            nuevo.setPersona(general.concat("Profesor - ", profesor.getNombre(), profesor.getApellido1(), profesor.getApellido2()));
        }
        return nuevo;
    }

    @GetMapping("/list_data_evaluacion")
    @ResponseBody
    public List<EvaluacionDelBeneficiarioDto> listDataEvaluacion(Long idBeneficiario) {
        return repoEvaluacion.findByIdBeneficiarioOrderByFechaDeEvaluacionDesc(idBeneficiario)
                .stream()
                .map(x -> convertDto(x))
                .collect(Collectors.toList());

    }

    @GetMapping("/marcar_leido_evaluacion")
    @ResponseBody
    public String marcarLeidoEvaluacion(Long idBeneficiario) {
        repoBeneficiario.tutorFormEvaluacionesMarcarLeido(idBeneficiario, general.getIdValidado());
        return "ok";
    }

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IComentarioRepository repocComentario;

    @GetMapping("/list_data_falta_asistencia")
    @ResponseBody
    public List<FaltaDto> listDataC(Long idBeneficiario) {
        return repoFalta.findByIdBeneficiarioOrderByFechaFaltaAsistenciaDesc(idBeneficiario).stream()
                .map(x -> convertDto(x))
                .collect(Collectors.toList());

    }

    @GetMapping("/marcar_leido_falta_de_asistencia")
    @ResponseBody
    public String marcarLeidoFaltaDeAsistencia(Long idBeneficiario) {
        repoBeneficiario.tutorFormFaltaAsistenciaMarcarLeido(idBeneficiario, general.getIdValidado());
        return "ok";
    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoBeneficiario.findByNombreContainingIgnoreCase(filtro).stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getNombre()))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        BeneficiarioEntity x = repoBeneficiario.findById(id).orElse(new BeneficiarioEntity());
        return x.getNombre();
    }

    @GetMapping("/list_agenda")
    @ResponseBody
    public List<Map> listaAgenda() {
        return repoBeneficiario.tutorListProximasActividades(general.getIdValidado());
    }
}
