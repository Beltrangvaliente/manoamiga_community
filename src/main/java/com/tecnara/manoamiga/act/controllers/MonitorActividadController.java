package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.converters.HorarioFinder;
import com.tecnara.manoamiga.act.dto.ActivityDto;
import com.tecnara.manoamiga.act.dto.FaltasDeAsistenciaDto;
import com.tecnara.manoamiga.act.dto.HorarioDto;
import com.tecnara.manoamiga.act.dto.MonitoresDeLaActividadDto;
import com.tecnara.manoamiga.act.dto.ParticipacionesBeneficiarioDto;
import com.tecnara.manoamiga.act.entities.ActividadEntity;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.FaltaAsistenciaEntity;
import com.tecnara.manoamiga.act.entities.HorarioEntity;
import com.tecnara.manoamiga.act.entities.MonitorEntity;
import com.tecnara.manoamiga.act.entities.ParticipacionBeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.ParticipacionMonitorEntity;
import com.tecnara.manoamiga.act.entities.ProfesorEntity;
import com.tecnara.manoamiga.act.entities.SolicitudEvaluacionMonitorEntity;
import com.tecnara.manoamiga.act.repositories.IActividadRepository;
import com.tecnara.manoamiga.act.repositories.IBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IFaltaAsistenciaRepository;
import com.tecnara.manoamiga.act.repositories.IHorarioRepository;
import com.tecnara.manoamiga.act.repositories.IMonitorRepository;
import com.tecnara.manoamiga.act.repositories.IParticipacionBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IParticipacionMonitorRepository;
import com.tecnara.manoamiga.act.repositories.IProfesorRepository;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tecnara.manoamiga.act.repositories.ISolicitudEvaluacionMonitorRepository;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@Controller
@RequestMapping("act/monitor/actividad")
public class MonitorActividadController extends TecnaraBaseController {

    @Autowired
    private IGeneral general;

    @Autowired
    private IActividadRepository repoActividad;

    @GetMapping("/list")
    public String list(Model m) {
        return "act/monitor/actividad_list";
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
        return repoActividad.monitorList(tipoActividad, nombre, fechaDesde, fechaHasta, responsable, general.getIdValidado());
    }
    

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new ActividadEntity());
        } else {
            m.addAttribute("registro", repoActividad.findById(id).get());
        }
        return "act/monitor/actividad_form";
    }
    
    @Autowired
    private IParticipacionMonitorRepository repoParticipacionMonitor;

    @PostMapping("/guardar")
    @Transactional
    public String guardar(ActividadEntity registro) {
        boolean nuevo=false;
        if (registro.getId()==null){
           nuevo=true; 
        }
        repoActividad.save(registro);
        if (nuevo==true){
            ParticipacionMonitorEntity parmon=new ParticipacionMonitorEntity();
            parmon.setIdActividad(registro.getId());
            parmon.setIdMonitor(general.getIdValidado());
            parmon.setPropietarioDeActividad("Si");
            repoParticipacionMonitor.save(parmon);
        }        
        return "redirect:list";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoActividad.deleteById(id);
        return "redirect:list";
    }

    @Autowired
    private IHorarioRepository repoHorario;

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

    @Autowired
    private IMonitorRepository repoMonitor;
    @Autowired
    private IBeneficiarioRepository repoBeneficiario;
    @Autowired
    private IProfesorRepository repoProfesor;

    @Autowired
    private IFaltaAsistenciaRepository repoFaltas;

    public FaltasDeAsistenciaDto convertDto(FaltaAsistenciaEntity origen) {
        FaltasDeAsistenciaDto nuevo = mapper.map(origen, FaltasDeAsistenciaDto.class);
        if(origen.getIdBeneficiario() !=null){
            BeneficiarioEntity beneficiario = repoBeneficiario.findById(origen.getIdBeneficiario()).orElse(new BeneficiarioEntity());
            nuevo.setBeneficiario(general.concat(beneficiario.getNombre(), beneficiario.getApellido1(), beneficiario.getApellido2()));
        }
        if (origen.getIdMonitor() != null) {
            MonitorEntity monitor = repoMonitor.findById(origen.getIdMonitor()).orElse(new MonitorEntity());
            nuevo.setPersona(general.concat(monitor.getNombre(), monitor.getApellido1(), monitor.getApellido2()));

        } else if (origen.getIdProfesor() != null) {
            ProfesorEntity profesor = repoProfesor.findById(origen.getIdProfesor()).orElse(new ProfesorEntity());
            nuevo.setPersona(general.concat(profesor.getNombre(), profesor.getApellido1(), profesor.getApellido2()));
        }

        return nuevo;
    }

    @GetMapping("/list_data_falta_asistencia")
    @ResponseBody
    public List<FaltasDeAsistenciaDto> listDataFalta(Long idActividad) {
        return repoFaltas.findByIdActividadOrderByFechaFaltaAsistenciaDesc(idActividad).stream()
                .map(x ->  convertDto(x))
                .collect(Collectors.toList());
    }

    public ParticipacionesBeneficiarioDto convertDto(ParticipacionBeneficiarioEntity origen) {
        ParticipacionesBeneficiarioDto nuevo = mapper.map(origen, ParticipacionesBeneficiarioDto.class);

        if (origen.getIdBeneficiario() != null) {
            BeneficiarioEntity beneficiario = repoBeneficiario.findById(origen.getIdBeneficiario()).orElse(new BeneficiarioEntity());
            nuevo.setNombreBeneficiario(beneficiario.getNombre());
            nuevo.setApellidoBeneficiario(general.concat(beneficiario.getApellido1(), beneficiario.getApellido2()));
        }

        return nuevo;
    }

    @Autowired
    private IParticipacionBeneficiarioRepository repoPartBeneficiario;

    @GetMapping("/list_data_beneficiario")
    @ResponseBody
    public List<ParticipacionesBeneficiarioDto> listDataBeneficiario(Long idActividad) {
        return repoPartBeneficiario.findByIdActividad(idActividad).stream()
                .map(x -> convertDto(x))
                .collect(Collectors.toList());
    }

    @Autowired
    private ModelMapper mapper;

    public MonitoresDeLaActividadDto
            convertDto(ParticipacionMonitorEntity origen) {
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

    @Autowired
    private ISolicitudEvaluacionMonitorRepository repoSolicitudEvaluacion;
    
    @GetMapping("/list_data_solicitudes_evaluaciones_monitores")
    @ResponseBody
    public List<SolicitudEvaluacionMonitorEntity> listDataSolicitudEvaluacion(Long idActividad) {
        return repoSolicitudEvaluacion.listDataSolicitudEvaluacion(idActividad);
                
    }
    
   
    @GetMapping("/list_data_evaluaciones_beneficiarios")
    @ResponseBody
    public List<Map> monitorFormEvaluaciones (Long idActividad) {
        return repoActividad.monitorFormEvaluaciones(idActividad);
                
    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro, Long idBeneficiario) {
        if (idBeneficiario == null) {
            return repoActividad.findByNombreActividadContainingIgnoreCase(filtro)
                    .stream()
                    .map(x -> new ClaveValorDto(x.getId(), x.getNombreActividad()))
                    .collect(Collectors.toList());
        } else { //Me envian el beneficiario.
            return repoActividad.monitorBuscadorFormBuscadorBeneficiario(filtro, idBeneficiario, general.getIdValidado()) //Buscar SQL filtrando por beneficiario.
                    .stream()
                    .map(x -> new ClaveValorDto(x.getId(), x.getNombreActividad()))
                    .collect(Collectors.toList());
        }

    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        ActividadEntity x = repoActividad.findById(id).orElse(new ActividadEntity());
        return x.getNombreActividad();
    }
}
