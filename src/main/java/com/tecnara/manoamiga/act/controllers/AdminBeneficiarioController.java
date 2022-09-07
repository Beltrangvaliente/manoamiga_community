package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.dto.ClaseFormativaDto;
import com.tecnara.manoamiga.act.dto.ComentarioParaBeneficiarioDto;
import com.tecnara.manoamiga.act.dto.EvaluacionParaBeneficiarioDto;
import com.tecnara.manoamiga.act.dto.FaltaAsistenciaParaBeneficiarioDto;
import com.tecnara.manoamiga.act.dto.MemoriaParaBeneficiarioDto;
import com.tecnara.manoamiga.act.dto.RegistroDeBeneficiarioActividadDto;
import com.tecnara.manoamiga.act.entities.ActividadEntity;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.ClaseFormativaEntity;
import com.tecnara.manoamiga.act.entities.ComentarioEntity;
import com.tecnara.manoamiga.act.entities.EvaluacionBeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.FaltaAsistenciaEntity;
import com.tecnara.manoamiga.act.entities.MemoriaEntity;
import com.tecnara.manoamiga.act.entities.MonitorEntity;
import com.tecnara.manoamiga.act.entities.ParticipacionBeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.ProfesorEntity;
import com.tecnara.manoamiga.act.entities.TutorEntity;
import com.tecnara.manoamiga.act.repositories.IActividadRepository;
import com.tecnara.manoamiga.act.repositories.IBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IClaseFormativaRepository;
import com.tecnara.manoamiga.act.repositories.IComentarioRepository;
import com.tecnara.manoamiga.act.repositories.IEvaluacionBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IEvaluacionMonitorRepository;
import com.tecnara.manoamiga.act.repositories.IFaltaAsistenciaRepository;
import com.tecnara.manoamiga.act.repositories.IMemoriaRepository;
import com.tecnara.manoamiga.act.repositories.IMonitorRepository;
import com.tecnara.manoamiga.act.repositories.IParticipacionBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IProfesorRepository;
import com.tecnara.manoamiga.act.repositories.ITutorRepository;
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

@Controller
@RequestMapping("/act/admin/beneficiario")
public class AdminBeneficiarioController extends TecnaraBaseController {

    @Autowired
    private IGeneral general;

    @Autowired
    private IBeneficiarioRepository repoBeneficiario;

    @Autowired
    private IUsuarioRepository repoUsuario;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new BeneficiarioEntity());
        } else {
            m.addAttribute("registro", repoBeneficiario.findById(id).get());
        }
        return "act/admin/beneficiario_form";
    }

    @GetMapping("/list")
    public String list(Model m) {
        m.addAttribute("nuevos", repoBeneficiario.adminContarUsuariosPendientes());
        return "act/admin/beneficiario_list";
    }

    
    @GetMapping("/list_data")
    @ResponseBody 
    public List<Map> listData(String filtro) {
        return repoBeneficiario.adminList(filtro);
                                 
    }
    
    @PostMapping("/guardar")
    public String guardar(BeneficiarioEntity registro) {
        repoBeneficiario.save(registro);
        return "redirect:list";
    }

    @PostMapping("/guardar_ajax")
    @ResponseBody
    public Long guardarAjax(BeneficiarioEntity registro) {
        repoBeneficiario.save(registro);
        return registro.getId();
    }

    
    @GetMapping("/borrar")
    @Transactional
    public String borrar(Long id) {
        BeneficiarioEntity ent=repoBeneficiario.getById(id);
        repoBeneficiario.deleteById(id);
        if (ent!=null && ent.getIdUsuario()!=null && repoUsuario.existsById(ent.getIdUsuario())){
            repoUsuario.deleteById(ent.getIdUsuario());
        }
        return "redirect:list";
    }
    
    

    @Autowired
    private IComentarioRepository repoComentario;

    @Autowired
    private IMonitorRepository repoMonitor;

    @Autowired
    private ITutorRepository repoTutor;

    @Autowired
    private IProfesorRepository repoProfesor;
    
    @Autowired
    private IParticipacionBeneficiarioRepository repoParticipacionBeneficiario;

    @Autowired
    private ModelMapper mapper;

    
    //HACER CONVERTO IDTUTOR
    @GetMapping("list_data_beneficiario")
    @ResponseBody
    public List<RegistroDeBeneficiarioActividadDto> listDataBeneficiarioActividadDtos(Long idBeneficiario) {
        return repoParticipacionBeneficiario.findById(idBeneficiario)
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

    @GetMapping("/list_data_comentario")
    @ResponseBody
    public List<ComentarioParaBeneficiarioDto> listDataComentario(Long idBeneficiario) {
        return repoComentario.findByIdBeneficiarioOrderByFechaComentarioDescHoraComentarioDesc(idBeneficiario).stream()
                .map(x -> convertDto(x))
                .collect(Collectors.toList());
    }

    @Autowired
    private IEvaluacionBeneficiarioRepository repoEvaBen;
    @Autowired
    private IEvaluacionMonitorRepository repoEvaMon;

    public EvaluacionParaBeneficiarioDto convertDto(EvaluacionBeneficiarioEntity origen) {
        EvaluacionParaBeneficiarioDto nuevo = mapper.map(origen, EvaluacionParaBeneficiarioDto.class);

        if (origen.getIdMonitor() != null) {

            MonitorEntity monitor = repoMonitor.findById(origen.getIdMonitor()).orElse(new MonitorEntity());
            nuevo.setEvaluador(general.concat("Monitor - ", monitor.getNombre(), monitor.getApellido1(), monitor.getApellido2()));

        } else if (origen.getIdProfesor() != null) {

            ProfesorEntity profesor = repoProfesor.findById(origen.getIdProfesor()).orElse(new ProfesorEntity());
            nuevo.setEvaluador(general.concat("Profesor - ", profesor.getNombre(), profesor.getApellido1(), profesor.getApellido2()));
        }

        return nuevo;
    }

    @GetMapping("/list_data_evaben")
    @ResponseBody
    public List<EvaluacionParaBeneficiarioDto> listDataEvaluacionBeneficiario(Long idBeneficiario) {
        return repoEvaBen.findByIdBeneficiario(idBeneficiario).stream().map(x -> convertDto(x)).collect(Collectors.toList());

    }

    @Autowired
    private IFaltaAsistenciaRepository repoFalta;

    @Autowired
    private IActividadRepository repoActividad;

    public FaltaAsistenciaParaBeneficiarioDto convertDto(FaltaAsistenciaEntity origen) {
        FaltaAsistenciaParaBeneficiarioDto nuevo = mapper.map(origen, FaltaAsistenciaParaBeneficiarioDto.class);

        if (origen.getIdMonitor() != null) {

            MonitorEntity monitor = repoMonitor.findById(origen.getIdMonitor()).orElse(new MonitorEntity());
            nuevo.setEvaluador(general.concat("Monitor - ", monitor.getNombre(), monitor.getApellido1(), monitor.getApellido2()));

        } else if (origen.getIdProfesor() != null) {

            ProfesorEntity profesor = repoProfesor.findById(origen.getIdProfesor()).orElse(new ProfesorEntity());
            nuevo.setEvaluador(general.concat("Profesor - ", profesor.getNombre(), profesor.getApellido1(), profesor.getApellido2()));
        }

        ActividadEntity actividad = repoActividad.findById(origen.getIdActividad()).orElse(new ActividadEntity());
        nuevo.setActividad(actividad.getNombreActividad());

        return nuevo;
    }

    @GetMapping("/list_data_falta")
    @ResponseBody
    public List<FaltaAsistenciaParaBeneficiarioDto> listDataFaltaAsistencia(Long idBeneficiario) {
        return repoFalta.findByIdBeneficiarioOrderByFechaFaltaAsistenciaDesc(idBeneficiario).stream().map(x -> convertDto(x)).collect(Collectors.toList());

    }

    @Autowired
    private IMemoriaRepository repoMemoria;

    public MemoriaParaBeneficiarioDto convertDto(MemoriaEntity origen) {
        MemoriaParaBeneficiarioDto nuevo = mapper.map(origen, MemoriaParaBeneficiarioDto.class);

        MonitorEntity monitor = repoMonitor.findById(origen.getIdMonitor()).orElse(new MonitorEntity());
        nuevo.setMonitor(general.concat("Monitor - ", monitor.getNombre(), monitor.getApellido1(), monitor.getApellido2()));
        return nuevo;
    }

    @GetMapping("/list_data_memoria")
    @ResponseBody
    public List<MemoriaParaBeneficiarioDto> listDataMemoria(Long idBeneficiario) {
        return repoMemoria.findByIdMonitor(idBeneficiario).stream().map(x -> convertDto(x)).collect(Collectors.toList());

    }

    @Autowired
    private IClaseFormativaRepository repoClaseFor;

    public ClaseFormativaDto convertDto(ClaseFormativaEntity origen) {
        ClaseFormativaDto nuevo = mapper.map(origen, ClaseFormativaDto.class);

        ProfesorEntity profesor = repoProfesor.findById(origen.getIdProfesor()).orElse(new ProfesorEntity());
        nuevo.setProfesor(general.concat("Profesor - ", profesor.getNombre(), profesor.getApellido1(), profesor.getApellido2()));
        return nuevo;
    }

    @GetMapping("/list_data_clase")
    @ResponseBody
    public List<ClaseFormativaDto> listDataClaseFormativa(Long idBeneficiario) {
        return repoClaseFor.findByIdProfesor(idBeneficiario).stream().map(x -> convertDto(x)).collect(Collectors.toList());

    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoBeneficiario.findByNombreContainingIgnoreCase(filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(),general.concat(x.getNombre(),x.getApellido1(),x.getApellido2())))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        BeneficiarioEntity x = repoBeneficiario.findById(id).orElse(new BeneficiarioEntity());
        return general.concat (x.getNombre(),x.getApellido1(),x.getApellido2()) ;
    
          
        
    
    }

    
    @GetMapping("/buscador_beneficiario_id")
    @ResponseBody
    public String buscadorTrabajador(Long id) {
        UsuarioEntity x = repoUsuario.findById(id).orElse(new UsuarioEntity());
        return x.getUsuario();
    }

    @GetMapping("/buscador_beneficiario")
    @ResponseBody
    public List<ClaveValorDto> buscadorTrabajador(String filtro, Long idUsuario) {
        return repoBeneficiario.adminFromBuscador(filtro, idUsuario) 
                .stream()
                .map(x -> new ClaveValorDto(Long.parseLong(x.get("id") + ""), x.get("usuario") + ""))
                .collect(Collectors.toList());

    }
    
    
}
