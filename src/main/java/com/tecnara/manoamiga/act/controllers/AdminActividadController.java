package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.converters.HorarioFinder;
import com.tecnara.manoamiga.act.dto.ActivityDto;
import com.tecnara.manoamiga.act.dto.FaltaAsistenciaParaBeneficiarioDto;
import com.tecnara.manoamiga.act.dto.FaltaDto;
import com.tecnara.manoamiga.act.dto.HorarioDto;
import com.tecnara.manoamiga.act.dto.MonitoresDeLaActividadDto;
import com.tecnara.manoamiga.act.dto.RegistroDeBeneficiarioActividadDto;
import com.tecnara.manoamiga.act.entities.ActividadEntity;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.ComentarioEntity;
import com.tecnara.manoamiga.act.entities.FaltaAsistenciaEntity;
import com.tecnara.manoamiga.act.entities.HorarioEntity;
import com.tecnara.manoamiga.act.entities.MonitorEntity;
import com.tecnara.manoamiga.act.entities.ParticipacionBeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.ParticipacionMonitorEntity;
import com.tecnara.manoamiga.act.entities.ProfesorEntity;
import com.tecnara.manoamiga.act.entities.TutorEntity;
import com.tecnara.manoamiga.act.repositories.IActividadRepository;
import com.tecnara.manoamiga.act.repositories.IBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IComentarioRepository;
import com.tecnara.manoamiga.act.repositories.IFaltaAsistenciaRepository;
import com.tecnara.manoamiga.act.repositories.IHorarioRepository;
import com.tecnara.manoamiga.act.repositories.IMonitorRepository;
import com.tecnara.manoamiga.act.repositories.IParticipacionBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IParticipacionMonitorRepository;
import com.tecnara.manoamiga.act.repositories.IProfesorRepository;
import com.tecnara.manoamiga.act.repositories.ITutorRepository;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/admin/actividad")
public class AdminActividadController extends TecnaraBaseController {

    @Autowired
    private IGeneral general;

    @Autowired
    private IActividadRepository repoActividad;

    @GetMapping("/list")
    public String list(Model m) {
        return "act/admin/actividad_list";
    }

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new ActividadEntity());
        } else {
            m.addAttribute("registro", repoActividad.findById(id).get());
        }
        return "act/admin/actividad_form";
    }

    @ModelAttribute("saludo")
    public String saludar() {
        return "Hola";
    }

    @Autowired
    private HorarioFinder horarioFinder;

    public ActivityDto convertDto(ActividadEntity origen) {
        ActivityDto nuevo = mapper.map(origen, ActivityDto.class);
        nuevo.setHorario(horarioFinder.findHorarioFromActivity(origen.getId()));
        return nuevo;
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData(String tipoActividad, String nombre,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta,
            String responsable) {
        return repoActividad.adminList(tipoActividad, nombre, fechaDesde, fechaHasta, responsable);
    }

    @PostMapping("/guardar")
    @Transactional
    public String guardar(ActividadEntity registro) {
        repoActividad.save(registro);
        return "redirect:list";

    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoActividad.deleteById(id);
        return "redirect:list";
    }

    @Autowired //1
    private IHorarioRepository repoHorario;
    
    @Autowired //4
    private IParticipacionMonitorRepository repoParticipacionMonitor;

    @Autowired
    private ModelMapper mapper;
    @Autowired //3
    private IParticipacionBeneficiarioRepository repoParticipacionBeneficiario;

    @Autowired
    private IBeneficiarioRepository repoBeneficiario;

    @Autowired
    private ITutorRepository repoTutor;

    @Autowired
    private IMonitorRepository repoMonitor;

    public HorarioDto convertDto(HorarioEntity origen) {
        HorarioDto nuevo = mapper.map(origen, HorarioDto.class);
        nuevo.setHorarioTexto(horarioFinder.getHorarioText(origen));
        return nuevo;
    }

    @GetMapping("/list_data_horario")
    @ResponseBody
    public List<HorarioDto> listDataHorario(Long idActividad) {
        return repoHorario.findByIdActividad(idActividad)
                .stream()
                .map(x -> convertDto(x))
                .collect(Collectors.toList());
    }

    @Autowired //2
    private IFaltaAsistenciaRepository repoFaltaAsistencia;

    @Autowired
    private IProfesorRepository repoProfesor;

    public FaltaAsistenciaParaBeneficiarioDto convertDto(FaltaAsistenciaEntity origen) {

        FaltaAsistenciaParaBeneficiarioDto nuevo = mapper.map(origen, FaltaAsistenciaParaBeneficiarioDto.class);

        if (origen.getIdBeneficiario() != null) {
            BeneficiarioEntity beneficiario = repoBeneficiario.findById(origen.getIdBeneficiario()).orElse(new BeneficiarioEntity());
            nuevo.setNombreBeneficiario(general.concat(beneficiario.getNombre(), beneficiario.getApellido1(), beneficiario.getApellido2()));
        }

        if (origen.getIdMonitor() != null) {

            MonitorEntity monitor = repoMonitor.findById(origen.getIdMonitor()).orElse(new MonitorEntity());
            nuevo.setEvaluador(general.concat("Monitor - ", monitor.getNombre(), monitor.getApellido1(), monitor.getApellido2()));

        } else if (origen.getIdProfesor() != null) {

            ProfesorEntity profesor = repoProfesor.findById(origen.getIdProfesor()).orElse(new ProfesorEntity());
            nuevo.setEvaluador(general.concat("Profesor - ", profesor.getNombre(), profesor.getApellido1()));
        }
        return nuevo;
    }

    @GetMapping("list_data_asistencia")
    @ResponseBody
    public List<FaltaAsistenciaParaBeneficiarioDto> listDataFaltaDeAsistencia(Long idActividad) {
        return repoFaltaAsistencia.findByIdActividad(idActividad)
                .stream()
                .map(x -> convertDto(x))
                .collect(Collectors.toList());
    }

    public RegistroDeBeneficiarioActividadDto convertDto(ParticipacionBeneficiarioEntity origen) {
        RegistroDeBeneficiarioActividadDto nuevo = mapper.map(origen, RegistroDeBeneficiarioActividadDto.class);
        if (origen.getIdBeneficiario() != null) {
            BeneficiarioEntity beneficiario = repoBeneficiario.findById(origen.getIdBeneficiario()).orElse(new BeneficiarioEntity());
            nuevo.setNombre(beneficiario.getNombre());
            nuevo.setApellido1(beneficiario.getApellido1());
            nuevo.setApellido2(beneficiario.getApellido2());
            nuevo.setDocumento(beneficiario.getDocumento());
            nuevo.setTelefono(beneficiario.getTelefono());

            if (beneficiario.getIdTutor() != null) {
                TutorEntity tutor = repoTutor.findById(beneficiario.getIdTutor()).orElse(new TutorEntity());
                nuevo.setNombreTutor(general.concat(tutor.getNombre(), tutor.getApellido()));

            }

        }

        return nuevo;
    }

    @GetMapping("/list_data_beneficiario")
    @ResponseBody
    public List<RegistroDeBeneficiarioActividadDto> listDataBeneficiarioActividadDtos(Long idActividad) {
        return repoParticipacionBeneficiario.findByIdActividad(idActividad).stream()
                .map(x -> convertDto(x))
                .collect(Collectors.toList());
    }

    public MonitoresDeLaActividadDto convertDto(ParticipacionMonitorEntity origen) {
        MonitoresDeLaActividadDto nuevo = mapper.map(origen, MonitoresDeLaActividadDto.class);

        if (origen.getIdMonitor() != null) {
            MonitorEntity monitor = repoMonitor.findById(origen.getIdMonitor()).orElse(new MonitorEntity());
            nuevo.setApellido1(monitor.getApellido1());
            nuevo.setApellido2(monitor.getApellido2());
            nuevo.setNombre(monitor.getNombre());
            nuevo.setCorreo(monitor.getCorreo());
            nuevo.setTelefono(monitor.getTelefono());
        }

        return nuevo;
    }

    @GetMapping("/list_data_monitor")
    @ResponseBody
    public List<MonitoresDeLaActividadDto> listDataMonitor(Long idActividad) {
        return repoParticipacionMonitor.findByIdActividad(idActividad).stream()
                .map(x -> convertDto(x))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoActividad.findByNombreActividadContainingIgnoreCase(filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getNombreActividad()))
                .collect(Collectors.toList());

        //return repoMonitor.findByNombreContainingIgnoreCase(filtro)
        //      .stream()
        //    .map(x -> new ClaveValorDto(x.getId(), x.getNombre()))
        //  .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        ActividadEntity x = repoActividad.findById(id).orElse(new ActividadEntity());
        return x.getNombreActividad();

    }

}
