package com.tecnara.manoamiga.act.controllers;

import com.tecnara.manoamiga.aaa.controllers.TecnaraBaseController;
import com.tecnara.manoamiga.act.dto.BeneficiarioDto;
import com.tecnara.manoamiga.act.dto.ClaseFormativaDto;
import com.tecnara.manoamiga.act.dto.ComentarioDto;
import com.tecnara.manoamiga.act.dto.EvaluacionDto;
import com.tecnara.manoamiga.act.dto.FaltaDto;
import com.tecnara.manoamiga.act.entities.ActividadEntity;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.ClaseFormativaEntity;
import com.tecnara.manoamiga.act.entities.ComentarioEntity;
import com.tecnara.manoamiga.act.entities.EvaluacionBeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.FaltaAsistenciaEntity;
import com.tecnara.manoamiga.act.entities.ProfesorEntity;
import com.tecnara.manoamiga.act.entities.TutorEntity;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.act.repositories.IActividadRepository;
import com.tecnara.manoamiga.act.repositories.IBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IClaseFormativaRepository;
import com.tecnara.manoamiga.act.repositories.IComentarioRepository;
import com.tecnara.manoamiga.act.repositories.IEvaluacionBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IFaltaAsistenciaRepository;
import com.tecnara.manoamiga.act.repositories.IProfesorRepository;
import com.tecnara.manoamiga.act.repositories.ITutorRepository;
import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.act.dto.ComentarioParaBeneficiarioDto;
import com.tecnara.manoamiga.act.entities.MonitorEntity;
import com.tecnara.manoamiga.expo.entities.AreaEntity;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.Spring;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/act/admin/profesor")
public class AdminProfesorController extends TecnaraBaseController {

    @Autowired
    private IGeneral general;

    @Autowired
    private IUsuarioRepository repoUsuario;

    @Autowired
    private IClaseFormativaRepository repoClaseFormativa;
    @Autowired
    private IProfesorRepository repoProfesor;
    @Autowired
    private ITutorRepository repoTutor;
    @Autowired
    private IEvaluacionBeneficiarioRepository repoEvaluacion_beneficiario;
    @Autowired
    private IFaltaAsistenciaRepository repoAsistencia;

    @Autowired
    private IComentarioRepository repoComentario;
    @Autowired
    private IBeneficiarioRepository repoBeneficiario;
    @Autowired
    private IActividadRepository repoActividad;

    @Autowired
    private ModelMapper mapper;

    public EvaluacionDto convertDto(EvaluacionBeneficiarioEntity origen) {
        EvaluacionDto nuevo = mapper.map(origen, EvaluacionDto.class);

        if(origen.getIdActividad()!=null){
            ActividadEntity actividad = repoActividad.findById(origen.getIdActividad()).orElse(new ActividadEntity());
            nuevo.setNombreActividad(actividad.getNombreActividad());
        }

        if (origen.getIdBeneficiario()!=null){
            BeneficiarioEntity beneficiario = repoBeneficiario.findById(origen.getIdBeneficiario()).orElse(new BeneficiarioEntity());
            nuevo.setNombreBeneficiario(general.concat( beneficiario.getNombre(), beneficiario.getApellido1() , beneficiario.getApellido2()));
            
        }

        return nuevo;
    }

    @GetMapping("/list_data_evaluacion")
    @ResponseBody
    public List<EvaluacionDto> ListDataProfesorEvaluacion(Long idProfesor) {
        return repoEvaluacion_beneficiario.findByIdProfesor(idProfesor)
                .stream()
                .map(x -> convertDto(x))
                .collect(Collectors.toList());
    }

    @Autowired
    private IFaltaAsistenciaRepository repoFaltaAsistencia;

    public FaltaDto convertDto(FaltaAsistenciaEntity origen) {

        FaltaDto nuevo = mapper.map(origen, FaltaDto.class);
        if (origen.getIdBeneficiario() != null) {

            BeneficiarioEntity beneficiario = repoBeneficiario.findById(origen.getIdBeneficiario()).orElse(new BeneficiarioEntity());
            nuevo.setNombreBeneficiario(general.concat(beneficiario.getNombre(), beneficiario.getApellido1(), beneficiario.getApellido2()));

            if (beneficiario.getIdTutor() != null) {

                TutorEntity tutor = repoTutor.findById(beneficiario.getIdTutor()).orElse(new TutorEntity());
                nuevo.setNombreTutor(general.concat(tutor.getNombre(), tutor.getApellido()));

            }

        }

        return nuevo;
    }

    @GetMapping("/list_data_falta")
    @ResponseBody
    public List<FaltaDto> listDataFalta(Long idProfesor) {
        return repoFaltaAsistencia.findByIdProfesor(idProfesor)
                .stream()
                .map(x -> convertDto(x))
                .collect(Collectors.toList());

    }

    @GetMapping("/list_data_comentario")
    @ResponseBody
    public List<ComentarioEntity> listDataComentario(Long idProfesor) {
        return repoComentario.findByIdProfesor(idProfesor);
    }

    @GetMapping("/list_data_clase")
    @ResponseBody
    public List<ClaseFormativaDto> listDataClaseFormativa(Long idProfesor) {

        return repoClaseFormativa.findByIdProfesor(idProfesor)
                .stream()
                .map(x -> convertDto(x))
                .collect(Collectors.toList());
    }

    public ClaseFormativaDto convertDto(ClaseFormativaEntity origen) {
        ClaseFormativaDto nuevo = mapper.map(origen, ClaseFormativaDto.class);
        
        if (origen.getIdBeneficiario()!=null){
            BeneficiarioEntity beneficiario = repoBeneficiario.findById(origen.getIdBeneficiario()).orElse(new BeneficiarioEntity());
            nuevo.setNombreBeneficiario(general.concat(beneficiario.getNombre(), beneficiario.getApellido1(), beneficiario.getApellido2()));
        }

        return nuevo;
    }

    @GetMapping("/list")
    public String list(Model m
    ) {
        return "act/admin/profesor_list";
    }

    @GetMapping("/list_data")
    @ResponseBody
    public List<ProfesorEntity> listData(String filtro) {
        return repoProfesor.findByNombreContainingIgnoreCaseOrApellido1ContainingOrApellido2Containing(filtro,filtro,filtro);
    }

    @GetMapping("/form")
    public String form(Model m, Long id) {
        if (id == null) {
            m.addAttribute("registro", new ProfesorEntity());
        } else {
            m.addAttribute("registro", repoProfesor.findById(id).get());
        }

        return "act/admin/profesor_form";

    }

    @PostMapping("/guardar")
    public String guardar(ProfesorEntity registro) {
        repoProfesor.save(registro);
        return "redirect:list";
    }
    
    @PostMapping("/guardar_ajax")
    @ResponseBody
    public Long guardarAjax(ProfesorEntity registro) {
        repoProfesor.save(registro);
        return registro.getId();
    }    

    @GetMapping("/borrar")
    @Transactional
    public String borrar(Long id) {
        ProfesorEntity ent=repoProfesor.getById(id);
        repoProfesor.deleteById(id);
        if (ent!=null && ent.getIdUsuario()!=null){
            repoUsuario.deleteById(ent.getIdUsuario());
        }        
        return "redirect:list";
    }

    @GetMapping("/buscador")
    @ResponseBody
    public List<ClaveValorDto> buscador(String nombre) {
        return repoProfesor.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(x -> new ClaveValorDto(x.getId(), x.getNombre()))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscador_id")
    @ResponseBody
    public String buscador(Long id) {
        UsuarioEntity x = repoUsuario.findById(id).orElse(new UsuarioEntity());
        return x.getUsuario();
    }
    
    @GetMapping("/buscador_profesor_id")
    @ResponseBody
    public String buscadorTrabajador(Long id) {
        UsuarioEntity x = repoUsuario.findById(id).orElse(new UsuarioEntity());
        return x.getUsuario();
    }

    @GetMapping("/buscador_profesor")
    @ResponseBody
    public List<ClaveValorDto> buscadorTrabajador(String filtro, Long idUsuario) {
        return repoProfesor.adminFromBuscador(filtro, idUsuario) 
                .stream()
                .map(x -> new ClaveValorDto(Long.parseLong(x.get("id") + ""), x.get("usuario") + ""))
                .collect(Collectors.toList());

    }    
}
