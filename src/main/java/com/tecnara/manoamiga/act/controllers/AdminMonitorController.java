package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.dto.ParticipacionMonitorDto;
import com.tecnara.manoamiga.act.dto.EvaluacionDelBeneficiarioDto;
import com.tecnara.manoamiga.act.dto.EvaluacionDelMonitorDto;
import com.tecnara.manoamiga.act.dto.MemoriaDto;
import com.tecnara.manoamiga.act.dto.MonitorDto;
import com.tecnara.manoamiga.act.dto.ValoracionDto;
import com.tecnara.manoamiga.act.entities.ActividadEntity;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.EvaluacionBeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.HorarioEntity;
import com.tecnara.manoamiga.act.entities.MemoriaEntity;
import com.tecnara.manoamiga.act.entities.MonitorEntity;
import com.tecnara.manoamiga.act.entities.ParticipacionMonitorEntity;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.act.repositories.IActividadRepository;
import com.tecnara.manoamiga.act.repositories.IBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IEvaluacionBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IEvaluacionMonitorRepository;
import com.tecnara.manoamiga.act.repositories.IHorarioRepository;
import com.tecnara.manoamiga.act.repositories.IMemoriaRepository;
import com.tecnara.manoamiga.act.repositories.IMonitorRepository;
import com.tecnara.manoamiga.act.repositories.IParticipacionMonitorRepository;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.entities.AdministradorEntity;
import com.tecnara.manoamiga.act.entities.EvaluacionMonitorEntity;
import com.tecnara.manoamiga.act.entities.ProfesorEntity;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/admin/monitor")
public class AdminMonitorController extends TecnaraBaseController {

    @Autowired
    private IUsuarioRepository repoUsuario;

    @Autowired
    private IMonitorRepository repoMonitor;

    @Autowired
    private IHorarioRepository repoHorario;

    @Autowired
    private IParticipacionMonitorRepository repoParticipacion;

    @Autowired
    private IActividadRepository repoActividad;

    @Autowired
    private IBeneficiarioRepository repoBeneficiario;

    @Autowired
    private IEvaluacionMonitorRepository repoEvaluacionMonitor;

    @Autowired
    private IEvaluacionBeneficiarioRepository repoEvaluacionBeneficiario;

    @Autowired
    private IMemoriaRepository repoMemoria;

    @Autowired
    private IGeneral general;

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new MonitorEntity());
        } else {
            m.addAttribute("registro", repoMonitor.findById(id).get());
        }

        return "act/admin/monitor_form";

    }

    @GetMapping("/list")
    public String list(Model m) {
        m.addAttribute("nuevos", repoMonitor.adminContarUsuariosPendientes());
        return "act/admin/monitor_list";
    }

    @Autowired
    private ModelMapper mapper;

    public MonitorDto convertDto(MonitorEntity origen) {
        MonitorDto nuevo = mapper.map(origen, MonitorDto.class);

        if (origen.getIdUsuario() != null) {
            UsuarioEntity usuario = repoUsuario.findById(origen.getIdUsuario()).orElse(new UsuarioEntity());
            nuevo.setUsuario(usuario.getUsuario());
        }

        return nuevo;
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<Map> listDataMonitoresList(String filtro) {
        return repoMonitor.adminMonitorList(filtro);
    }

    public ParticipacionMonitorDto convertDTO(ParticipacionMonitorEntity origen) {
        ParticipacionMonitorDto nuevo = mapper.map(origen, ParticipacionMonitorDto.class);

        ActividadEntity actividad = repoActividad.findById(origen.getIdActividad()).orElse(new ActividadEntity());
        nuevo.setNombreActividad(actividad.getNombreActividad());

        HorarioEntity fechaInicio = new HorarioEntity();
        List<HorarioEntity> lista = repoHorario.findByIdActividad(origen.getId());
        if (lista.size() > 0) {
            fechaInicio = lista.get(0);
        }
        nuevo.setFechaInicio(fechaInicio.getFechaInicio());

        return nuevo;
    }

    @GetMapping("/list_data_actividad")
    @ResponseBody
    public List<ParticipacionMonitorDto> listDataActividad(Long idMonitor) {
        return repoParticipacion.findByIdMonitor(idMonitor).stream()
                .map(x -> convertDTO(x))
                .collect(Collectors.toList());
    }

    public EvaluacionDelMonitorDto convertDTO(EvaluacionBeneficiarioEntity origen) {
        EvaluacionDelMonitorDto nuevo = mapper.map(origen, EvaluacionDelMonitorDto.class);

        BeneficiarioEntity nombre = repoBeneficiario.findById(origen.getIdBeneficiario()).orElse(new BeneficiarioEntity());
        nuevo.setNombre(nombre.getNombre());

        return nuevo;

    }

    @GetMapping("/list_data_evaluacion")
    @ResponseBody
    public List<EvaluacionDelBeneficiarioDto> listDataEvaluacion(Long idMonitor) {
        return repoEvaluacionBeneficiario.findByIdMonitor(idMonitor).stream()
                .map(x -> convertDTO2(x))
                .collect(Collectors.toList());

    }

    public EvaluacionDelBeneficiarioDto convertDTO2(EvaluacionBeneficiarioEntity origen) {
        EvaluacionDelBeneficiarioDto nuevo = mapper.map(origen, EvaluacionDelBeneficiarioDto.class);

        BeneficiarioEntity nombre = repoBeneficiario.findById(origen.getIdBeneficiario()).orElse(new BeneficiarioEntity());
        nuevo.setNombre(general.concat(nombre.getNombre(), nombre.getApellido1(), nombre.getApellido2()));

        return nuevo;

    }

    @GetMapping("/list_data_comentario")
    @ResponseBody
    public List<EvaluacionDelBeneficiarioDto> listDataComentario(Long idMonitor) {
        return repoEvaluacionBeneficiario.findByIdMonitor(idMonitor).stream()
                .map(x -> convertDTO2(x))
                .collect(Collectors.toList());
    }

    public MemoriaDto convertDTO(MemoriaEntity origen) {
        MemoriaDto nuevo = mapper.map(origen, MemoriaDto.class);

        BeneficiarioEntity nombre = repoBeneficiario.findById(origen.getIdBeneficiario()).orElse(new BeneficiarioEntity());
        nuevo.setNombre(general.concat(nombre.getNombre(), nombre.getApellido1(), nombre.getApellido2()));

        return nuevo;

    }

    @GetMapping("/list_data_memoria")
    @ResponseBody
    public List<MemoriaDto> listDataMemoria(Long idMonitor) {
        return repoMemoria.findByIdMonitor(idMonitor).stream()
                .map(x -> convertDTO(x))
                .collect(Collectors.toList());

    }

    public ValoracionDto convertDTO3(EvaluacionBeneficiarioEntity origen) {
        ValoracionDto nuevo = mapper.map(origen, ValoracionDto.class);

        BeneficiarioEntity nombre = repoBeneficiario.findById(origen.getIdBeneficiario()).orElse(new BeneficiarioEntity());
        nuevo.setNombre(general.concat(nombre.getNombre(), nombre.getApellido1(), nombre.getApellido2()));

        return nuevo;

    }

        public EvaluacionDelMonitorDto convertDTO(EvaluacionMonitorEntity  origen) {
        EvaluacionDelMonitorDto nuevo = mapper.map(origen, EvaluacionDelMonitorDto.class);

        BeneficiarioEntity nombre = repoBeneficiario.findById(origen.getIdBeneficiario()).orElse(new BeneficiarioEntity());
        nuevo.setNombre(nombre.getNombre());

        return nuevo;

    }
    
    @GetMapping("/list_data_valoracion")
    @ResponseBody
    public List<Map> listDataValoracion(Long idMonitor) {
        return repoMonitor.adminFormOpiniones(idMonitor);
    }

    @GetMapping("/help")
    public String help(Model m) {
        return "act/admin/monitor_help";
    }

    @PostMapping("/guardar")
    public String guardar(MonitorEntity registro) {
        repoMonitor.save(registro);
        return "redirect:list";
    }
    
    @PostMapping("/guardar_ajax")
    @ResponseBody
    public Long guardarAjax(MonitorEntity registro) {
        repoMonitor.save(registro);
        return registro.getId();
    }       

    @GetMapping("/borrar")
    @Transactional
    public String borrar(Long id) {
        MonitorEntity ent=repoMonitor.getById(id);
        repoMonitor.deleteById(id);
        if (ent!=null && ent.getIdUsuario()!=null){
            repoUsuario.deleteById(ent.getIdUsuario());
        }        
        return "redirect:list";
    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String filtro) {
        return repoMonitor.findByNombreContainingIgnoreCaseOrApellido1ContainingIgnoreCaseOrApellido2ContainingIgnoreCase(filtro, filtro, filtro)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), general.concat(x.getNombre(), x.getApellido1(), x.getApellido2())))
                .collect(Collectors.toList());

    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        MonitorEntity x = repoMonitor.findById(id).orElse(new MonitorEntity());
        return general.concat(x.getNombre(), x.getApellido1(), x.getApellido2());
    }

    @GetMapping("/buscador_monitor")
    @ResponseBody
    public List<ClaveValorDto> buscadorMon(String filtro, Long idUsuario) {
        return repoMonitor.adminMonitorFromBuscador(filtro, idUsuario).stream()
                .map(x -> new ClaveValorDto(Long.parseLong(x.get("id") + ""), x.get("usuario") + ""))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/buscador_monitor_id")
    @ResponseBody
    public String buscadorMon(Long id){
       UsuarioEntity x= repoUsuario.findById(id).orElse(new UsuarioEntity());
       return x.getUsuario();
    }

}
