package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.dto.BeneficiarioDto;
import com.tecnara.manoamiga.act.dto.ComentarioMonitorDto;
import com.tecnara.manoamiga.act.dto.ComentarioParaBeneficiarioDto;
import com.tecnara.manoamiga.act.dto.EvaluacionDelBeneficiarioDto;
import com.tecnara.manoamiga.act.dto.FaltaDto;

import com.tecnara.manoamiga.act.dto.MonitorDto;
import com.tecnara.manoamiga.act.dto.NombreMonitorDto;
import com.tecnara.manoamiga.act.dto.NumeroFaltasDTO;
import com.tecnara.manoamiga.act.dto.TutorDto;
import com.tecnara.manoamiga.act.entities.ActividadEntity;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.ComentarioEntity;
import com.tecnara.manoamiga.act.entities.EvaluacionBeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.EvaluacionMonitorEntity;
import com.tecnara.manoamiga.act.entities.FaltaAsistenciaEntity;
import com.tecnara.manoamiga.act.entities.MemoriaEntity;
import com.tecnara.manoamiga.act.entities.MonitorEntity;
import com.tecnara.manoamiga.act.entities.ProfesorEntity;
import com.tecnara.manoamiga.act.entities.TutorEntity;
import com.tecnara.manoamiga.act.repositories.IBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IComentarioRepository;
import com.tecnara.manoamiga.act.repositories.IEvaluacionBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IEvaluacionMonitorRepository;
import com.tecnara.manoamiga.act.repositories.IFaltaAsistenciaRepository;
import com.tecnara.manoamiga.act.repositories.IMemoriaRepository;
import com.tecnara.manoamiga.act.repositories.IMonitorRepository;
import com.tecnara.manoamiga.act.repositories.IProfesorRepository;
import com.tecnara.manoamiga.act.repositories.ITutorRepository;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.expo.dto.EventoDto;
import com.tecnara.manoamiga.expo.entities.AreaEntity;
import com.tecnara.manoamiga.expo.entities.EspacioEntity;
import com.tecnara.manoamiga.expo.entities.EventoEntity;
import com.tecnara.manoamiga.expo.entities.TrabajadorEntity;
import com.tecnara.manoamiga.expo.repositories.IAreaRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/monitor/beneficiario")
public class MonitorBeneficiarioController extends TecnaraBaseController {

    @Autowired
    private IBeneficiarioRepository repoBeneficiario;

    @Autowired
    private IEvaluacionMonitorRepository repoEvaluacionmonitor;

    @GetMapping("/list")
    public String list(Model m) {   //HTML
        return "act/monitor/beneficiario_list";
    }

    @GetMapping("/form")
    public String form(Model m, Long id) {   //HTML
        if (id == null) {
            m.addAttribute("registro", new BeneficiarioEntity());
        } else {
            m.addAttribute("registro", repoBeneficiario.findById(id).get());
        }
        return "act/monitor/beneficiario_form";

    }
    @Autowired
    private IProfesorRepository repoProfesor;

    public ComentarioParaBeneficiarioDto convertDto(ComentarioEntity origen) {
        ComentarioParaBeneficiarioDto nuevo = mapper.map(origen, ComentarioParaBeneficiarioDto.class);
        if (origen.getIdMonitor() != null) {
            MonitorEntity monitor = repoMonitor.findById(origen.getIdMonitor()).orElse(new MonitorEntity());
            nuevo.setPersona(general.concat("Monitor - ", monitor.getNombre(), monitor.getApellido1()));
        } else if (origen.getIdTutor() != null) {
            TutorEntity tutor = repoTutor.findById(origen.getIdTutor()).orElse(new TutorEntity());
            nuevo.setPersona(general.concat("Tutor - ", tutor.getNombre(), tutor.getApellido()));
        } else if (origen.getIdProfesor() != null) {
            ProfesorEntity profesor = repoProfesor.findById(origen.getIdProfesor()).orElse(new ProfesorEntity());
            nuevo.setPersona(general.concat("Profesor - ", profesor.getNombre(), profesor.getApellido1()));
        }
        return nuevo;
    }

    @GetMapping("/list_data_autor")
    @ResponseBody
    public List<ComentarioParaBeneficiarioDto> listDataAutor(Long idBeneficiario) {
        return repoComentario.findByIdBeneficiarioOrderByFechaComentarioDescHoraComentarioDesc(idBeneficiario).stream()
                .map(x -> convertDto(x))
                .collect(Collectors.toList());
    }

    @Autowired
    private IEvaluacionBeneficiarioRepository repoEvBeneficiario;

    @Autowired
    private IEvaluacionMonitorRepository repoEvMonitor;

    @GetMapping("/list_data_eval_monitor")
    @ResponseBody
    public List<EvaluacionMonitorEntity> listDataEvMonitor(Long idBeneficiario) {
        return repoEvMonitor.findByIdMonitorAndIdBeneficiarioOrderByFechaComentarioDesc(general.getIdValidado(), idBeneficiario);
    }

    @Autowired
    private IMonitorRepository repoMonitor;

    public EvaluacionDelBeneficiarioDto convertDto(EvaluacionBeneficiarioEntity origen) {
        EvaluacionDelBeneficiarioDto nuevo = mapper.map(origen, EvaluacionDelBeneficiarioDto.class);

        if (origen.getIdMonitor() != null) {
            MonitorEntity mon = repoMonitor.findById(origen.getIdMonitor()).orElse(new MonitorEntity());
            nuevo.setPersona(general.concat(mon.getNombre(), mon.getApellido1(), mon.getApellido2()));
        }
        return nuevo;

    }

    @GetMapping("/list_data_nombre")
    @ResponseBody
    public List<EvaluacionDelBeneficiarioDto> listDataNombre(Long idBeneficiario) {
        return repoEvBeneficiario.findByIdBeneficiarioOrderByFechaDeEvaluacionDesc(idBeneficiario).stream().map(x -> convertDto(x))
                .collect(Collectors.toList());
    }

    @Autowired
    private IComentarioRepository repoComentario;

    public ComentarioMonitorDto convertDTO(ComentarioEntity origen) {
        ComentarioMonitorDto nuevo = mapper.map(origen, ComentarioMonitorDto.class);

        if (origen.getIdMonitor() != null) {
            MonitorEntity mon = repoMonitor.findById(origen.getIdMonitor()).orElse(new MonitorEntity());
            nuevo.setNombreMonitor(general.concat("Monitor - ", mon.getNombre(), mon.getApellido1(), mon.getApellido2()));
        } else if (origen.getIdTutor() != null) {
            TutorEntity tutor = repoTutor.findById(origen.getIdTutor()).orElse(new TutorEntity());
            nuevo.setNombreMonitor(general.concat("Tutor - ", tutor.getNombre(), tutor.getApellido()));
        } else if (origen.getIdProfesor() != null) {
            ProfesorEntity profesor = repoProfesor.findById(origen.getIdProfesor()).orElse(new ProfesorEntity());
            nuevo.setNombreMonitor(general.concat("Profesor - ", profesor.getNombre(), profesor.getApellido1()));
        }
        return nuevo;
    }

    @GetMapping("/list_data_comentario")
    @ResponseBody
    public List<ComentarioMonitorDto> listDataComentario(Long idBeneficiario) {
        return repoComentario.findByIdBeneficiarioOrderByFechaComentarioDescHoraComentarioDesc(idBeneficiario).stream().map(x -> convertDTO(x))
                .collect(Collectors.toList());
    }

    @Autowired
    private IMemoriaRepository repoMemo;

    @GetMapping("/list_data_memoria")
    @ResponseBody
    public List<MemoriaEntity> listDataMemoria(Long idBeneficiario) {
        return repoMemo.findByIdMonitorAndIdBeneficiarioOrderByFechaComentarioDesc(general.getIdValidado(), idBeneficiario);
    }

    public NombreMonitorDto convertDto(MemoriaEntity origen) {
        NombreMonitorDto nuevo = mapper.map(origen, NombreMonitorDto.class);
        if (origen.getIdMonitor() != null) {
            MonitorEntity memo = repoMonitor.findById(origen.getIdMonitor()).orElse(new MonitorEntity());
            nuevo.setNombreCompleto(general.concat(memo.getNombre(), memo.getApellido1(), memo.getApellido2()));
        }
        return nuevo;
    }

    @GetMapping("/list_data_memo")
    @ResponseBody
    public List<NombreMonitorDto> listDataMemo(Long idBeneficiario) {
        return repoMemo.findByIdMonitorAndIdBeneficiarioOrderByFechaComentarioDesc(general.getIdValidado(), idBeneficiario).stream().map(x -> convertDto(x)).collect(Collectors.toList());
    }

    @Autowired
    private IFaltaAsistenciaRepository repoFalta;

    public FaltaDto convertDto(FaltaAsistenciaEntity origen) {

        FaltaDto nuevo = mapper.map(origen, FaltaDto.class);

        if (origen.getIdBeneficiario() != null) {
            BeneficiarioEntity ben = repoBeneficiario.findById(origen.getIdBeneficiario()).orElse(new BeneficiarioEntity());
            nuevo.setNombreBeneficiario(general.concat(ben.getNombre(), ben.getApellido1(), ben.getApellido2()));
        }

        return nuevo;
    }

    @GetMapping("/list_data_falta")
    @ResponseBody
    public List<FaltaDto> listDataNumeroFalta(Long idBeneficiario) {
        return repoFalta.findByIdBeneficiarioOrderByFechaFaltaAsistenciaDesc(idBeneficiario)
                .stream()
                .map(x -> convertDto(x))
                .collect(Collectors.toList());
    }

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ITutorRepository repoTutor;

    public BeneficiarioDto convertDto(BeneficiarioEntity origen) {
        BeneficiarioDto nuevo = mapper.map(origen, BeneficiarioDto.class);

        if (origen.getIdTutor() != null) {

            TutorEntity tut = repoTutor.findById(origen.getIdTutor()).orElse(new TutorEntity());
            nuevo.setNombreCompletoTutor(general.concat(tut.getNombre(), tut.getApellido()));

        }
        return nuevo;
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listData(String filtro) {
        return repoBeneficiario.monitorList(filtro);
    }

    @PostMapping("/guardar")
    public String guardar(BeneficiarioEntity registro) {
        repoBeneficiario.save(registro);
        return "redirect:list";
    }

    @GetMapping("/borrar")
    public String borrar(Long id) {
        repoBeneficiario.deleteById(id);
        return "redirect:list";
    }

    @Autowired
    private IGeneral general;

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro, Long idActividad) {
        if (idActividad == null) {
            return repoBeneficiario.findByNombreContainingIgnoreCase(filtro)
                    .stream()
                    .map(x -> new ClaveValorDto(x.getId(), general.concat(x.getNombre(), x.getApellido1(), x.getApellido2())))
                    .collect(Collectors.toList());
        } else { // Nos envía la actividad
            return repoBeneficiario.monitorBuscadorFormBuscadorActividad(idActividad, filtro, general.getIdValidado())
                    .stream()
                    .map(x -> new ClaveValorDto(Long.parseLong(x.get("id") + ""), x.get("nombreCompleto") + ""))
                    .collect(Collectors.toList()); // Buscar SQL filtrando por actividad

        }
    }

    @Autowired
    private IUsuarioRepository repoUsuario;

    @GetMapping("/buscador_beneficiario")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        //Leer los beneficiarios
        List<UsuarioEntity> usuario = repoUsuario.monitorBeneficiarioBuscador(filtro);
        //Convertir los beneficiarios
        List<ClaveValorDto> res = usuario.stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getUsuario()))
                .collect(Collectors.toList());
        //Devolver el resultado
        return res;

    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        BeneficiarioEntity x = repoBeneficiario.findById(id).orElse(new BeneficiarioEntity());
        return general.concat(x.getNombre(), x.getApellido1(), x.getApellido2());

    }
}
